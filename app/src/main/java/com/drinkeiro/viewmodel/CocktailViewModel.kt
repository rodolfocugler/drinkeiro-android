package com.drinkeiro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drinkeiro.data.model.Cocktail
import com.drinkeiro.data.repository.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private var _idCounter = 90000
private fun newId() = "custom_${++_idCounter}"

private fun emptyCocktail() = Cocktail(
    idDrink       = newId(),
    strDrink      = "",
    strCategory   = "Cocktail",
    strAlcoholic  = "Alcoholic",
    strGlass      = "",
    strIngredient = emptyList(),
)

private const val PAGE_SIZE = 20

data class CocktailUiState(
    val cocktails:             List<Cocktail> = emptyList(),
    val favorites:             Set<String>    = emptySet(),
    val selectedCategory:      String         = "All",
    val searchQuery:           String         = "",
    val isLoading:             Boolean        = false,
    val isLoadingMore:         Boolean        = false,
    val isRefreshing:          Boolean        = false,
    val canLoadMore:           Boolean        = true,
    val currentPage:           Int            = 0,
    val error:                 String?        = null,
    val toastMessage:          String?        = null,
    val recipeEditorCocktail:  Cocktail?      = null,
    val isCreatingRecipe:      Boolean        = false,
    val deleteConfirmCocktail: Cocktail?      = null,
) {
    // Filtered view applying search query on top of loaded cocktails
    val filtered: List<Cocktail> get() {
        val q = searchQuery.trim().lowercase()
        return if (q.isBlank()) cocktails
        else cocktails.filter { c ->
            c.strDrink.lowercase().contains(q) ||
            c.strIngredient.any { it.strIngredient.lowercase().contains(q) } ||
            c.strCategory.lowercase().contains(q)
        }
    }
    val favFiltered: List<Cocktail> get() = filtered.filter { favorites.contains(it.idDrink) }
}

@HiltViewModel
class CocktailViewModel @Inject constructor(
    private val repo: CocktailRepository,
) : ViewModel() {

    private val _ui = MutableStateFlow(CocktailUiState())
    val ui = _ui.asStateFlow()

    init { load(refresh = false) }

    // ── Load / Refresh / Load more ────────────────────────────────────────────

    fun load(refresh: Boolean = false) {
        if (_ui.value.isLoading || _ui.value.isRefreshing) return
        viewModelScope.launch {
            if (refresh) {
                _ui.update { it.copy(isRefreshing = true, currentPage = 0, canLoadMore = true) }
            } else {
                _ui.update { it.copy(isLoading = true, error = null) }
            }

            val cat = _ui.value.selectedCategory.takeIf { it != "All" }
            val cocktailsResult = repo.getCocktails(category = cat, page = 0, pageSize = PAGE_SIZE)
            val favoritesResult = repo.getFavorites()

            _ui.update { state ->
                state.copy(
                    isLoading    = false,
                    isRefreshing = false,
                    currentPage  = 0,
                    cocktails    = cocktailsResult.getOrElse { state.cocktails },
                    canLoadMore  = (cocktailsResult.getOrElse { emptyList() }.size >= PAGE_SIZE),
                    favorites    = favoritesResult.getOrElse { emptyList() }.map { it.idDrink }.toSet(),
                    error        = cocktailsResult.exceptionOrNull()?.message,
                )
            }
        }
    }

    fun loadMore() {
        val state = _ui.value
        if (!state.canLoadMore || state.isLoadingMore || state.isLoading || state.isRefreshing) return
        val nextPage = state.currentPage + 1
        viewModelScope.launch {
            _ui.update { it.copy(isLoadingMore = true) }
            val cat = state.selectedCategory.takeIf { it != "All" }
            repo.getCocktails(category = cat, page = nextPage, pageSize = PAGE_SIZE)
                .onSuccess { newItems ->
                    _ui.update { s ->
                        s.copy(
                            isLoadingMore = false,
                            currentPage   = nextPage,
                            cocktails     = s.cocktails + newItems,
                            canLoadMore   = newItems.size >= PAGE_SIZE,
                        )
                    }
                }
                .onFailure {
                    _ui.update { it.copy(isLoadingMore = false) }
                }
        }
    }

    fun refresh() = load(refresh = true)

    // ── Search ────────────────────────────────────────────────────────────────

    fun setSearch(query: String) {
        _ui.update { it.copy(searchQuery = query) }
    }

    // ── Category ──────────────────────────────────────────────────────────────

    fun setCategory(category: String) {
        if (_ui.value.selectedCategory == category) return
        _ui.update { it.copy(selectedCategory = category, searchQuery = "", currentPage = 0, canLoadMore = true) }
        load(refresh = false)
    }

    // ── Favorites ─────────────────────────────────────────────────────────────

    fun toggleFavorite(idDrink: String) {
        val isFav = _ui.value.favorites.contains(idDrink)
        _ui.update { s ->
            s.copy(favorites = if (isFav) s.favorites - idDrink else s.favorites + idDrink)
        }
        viewModelScope.launch {
            val result = if (isFav) repo.removeFavorite(idDrink) else repo.addFavorite(idDrink)
            if (result.isFailure) {
                _ui.update { s ->
                    s.copy(favorites = if (isFav) s.favorites + idDrink else s.favorites - idDrink)
                }
            }
        }
    }

    // ── CRUD ──────────────────────────────────────────────────────────────────

    fun createCocktail(cocktail: Cocktail) {
        viewModelScope.launch {
            repo.createCocktail(cocktail).onSuccess { created ->
                _ui.update { s ->
                    s.copy(
                        cocktails            = listOf(created) + s.cocktails,
                        recipeEditorCocktail = null,
                        toastMessage         = "${created.strDrink} created!",
                    )
                }
            }.onFailure { e -> _ui.update { it.copy(error = e.message) } }
        }
    }

    fun updateCocktail(cocktail: Cocktail) {
        viewModelScope.launch {
            repo.updateCocktail(cocktail).onSuccess { updated ->
                _ui.update { s ->
                    s.copy(
                        cocktails            = s.cocktails.map { if (it.idDrink == updated.idDrink) updated else it },
                        recipeEditorCocktail = null,
                        toastMessage         = "${updated.strDrink} updated!",
                    )
                }
            }.onFailure { e -> _ui.update { it.copy(error = e.message) } }
        }
    }

    fun deleteCocktail(idDrink: String) {
        viewModelScope.launch {
            repo.deleteCocktail(idDrink).onSuccess {
                _ui.update { s ->
                    s.copy(
                        cocktails             = s.cocktails.filter { it.idDrink != idDrink },
                        favorites             = s.favorites - idDrink,
                        deleteConfirmCocktail = null,
                        toastMessage          = "Recipe deleted",
                    )
                }
            }.onFailure { e -> _ui.update { it.copy(error = e.message) } }
        }
    }

    // ── Recipe editor ─────────────────────────────────────────────────────────

    fun requestCreateRecipe() =
        _ui.update { it.copy(recipeEditorCocktail = emptyCocktail(), isCreatingRecipe = true) }

    fun requestEdit(cocktail: Cocktail) =
        _ui.update { it.copy(recipeEditorCocktail = cocktail, isCreatingRecipe = false) }

    fun dismissRecipeEditor() =
        _ui.update { it.copy(recipeEditorCocktail = null) }

    fun requestDelete(cocktail: Cocktail) =
        _ui.update { it.copy(deleteConfirmCocktail = cocktail) }

    fun dismissDeleteConfirm() =
        _ui.update { it.copy(deleteConfirmCocktail = null) }

    fun dismissToast() = _ui.update { it.copy(toastMessage = null) }
    fun dismissError()  = _ui.update { it.copy(error = null) }
}
