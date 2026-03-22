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

data class CocktailUiState(
    val cocktails:             List<Cocktail> = emptyList(),
    val favorites:             Set<String>    = emptySet(),
    val selectedCategory:      String         = "All",
    val isLoading:             Boolean        = false,
    val error:                 String?        = null,
    val toastMessage:          String?        = null,
    // Recipe editor — non-null means the editor sheet is open
    val recipeEditorCocktail:  Cocktail?      = null,
    val isCreatingRecipe:      Boolean        = false,
    // Delete confirmation — non-null means the confirm dialog is open
    val deleteConfirmCocktail: Cocktail?      = null,
)

@HiltViewModel
class CocktailViewModel @Inject constructor(
    private val repo: CocktailRepository,
) : ViewModel() {

    private val _ui = MutableStateFlow(CocktailUiState())
    val ui = _ui.asStateFlow()

    init { loadAll() }

    // ── Load ──────────────────────────────────────────────────────────────────

    fun loadAll() {
        viewModelScope.launch {
            _ui.update { it.copy(isLoading = true, error = null) }
            val cocktailsResult = repo.getCocktails()
            val favoritesResult = repo.getFavorites()
            _ui.update { state ->
                state.copy(
                    isLoading = false,
                    cocktails = cocktailsResult.getOrElse { state.cocktails },
                    favorites = favoritesResult.getOrElse { emptyList() }
                        .map { it.idDrink }.toSet(),
                    error = cocktailsResult.exceptionOrNull()?.message,
                )
            }
        }
    }

    fun setCategory(category: String) {
        _ui.update { it.copy(selectedCategory = category) }
        val cat = if (category == "All") null else category
        viewModelScope.launch {
            repo.getCocktails(cat).onSuccess { list ->
                _ui.update { it.copy(cocktails = list) }
            }
        }
    }

    // ── Favorites ─────────────────────────────────────────────────────────────

    fun toggleFavorite(idDrink: String) {
        val isFav = _ui.value.favorites.contains(idDrink)
        _ui.update { state ->
            val newFavs = if (isFav) state.favorites - idDrink else state.favorites + idDrink
            state.copy(favorites = newFavs)
        }
        viewModelScope.launch {
            val result = if (isFav) repo.removeFavorite(idDrink) else repo.addFavorite(idDrink)
            if (result.isFailure) {
                _ui.update { state ->
                    val reverted = if (isFav) state.favorites + idDrink else state.favorites - idDrink
                    state.copy(favorites = reverted, error = result.exceptionOrNull()?.message)
                }
            }
        }
    }

    // ── CRUD ──────────────────────────────────────────────────────────────────

    fun createCocktail(cocktail: Cocktail) {
        viewModelScope.launch {
            repo.createCocktail(cocktail).onSuccess { created ->
                _ui.update { state ->
                    state.copy(
                        cocktails            = state.cocktails + created,
                        recipeEditorCocktail = null,
                        toastMessage         = "${created.strDrink} created!",
                    )
                }
            }.onFailure { e ->
                _ui.update { it.copy(error = e.message) }
            }
        }
    }

    fun updateCocktail(cocktail: Cocktail) {
        viewModelScope.launch {
            repo.updateCocktail(cocktail).onSuccess { updated ->
                _ui.update { state ->
                    state.copy(
                        cocktails            = state.cocktails.map {
                            if (it.idDrink == updated.idDrink) updated else it
                        },
                        recipeEditorCocktail = null,
                        toastMessage         = "${updated.strDrink} updated!",
                    )
                }
            }.onFailure { e ->
                _ui.update { it.copy(error = e.message) }
            }
        }
    }

    fun deleteCocktail(idDrink: String) {
        viewModelScope.launch {
            repo.deleteCocktail(idDrink).onSuccess {
                _ui.update { state ->
                    state.copy(
                        cocktails             = state.cocktails.filter { it.idDrink != idDrink },
                        favorites             = state.favorites - idDrink,
                        deleteConfirmCocktail = null,
                        toastMessage          = "Recipe deleted",
                    )
                }
            }.onFailure { e ->
                _ui.update { it.copy(error = e.message) }
            }
        }
    }

    // ── Recipe editor ─────────────────────────────────────────────────────────

    fun requestCreateRecipe() {
        _ui.update { it.copy(recipeEditorCocktail = emptyCocktail(), isCreatingRecipe = true) }
    }

    fun requestEdit(cocktail: Cocktail) {
        _ui.update { it.copy(recipeEditorCocktail = cocktail, isCreatingRecipe = false) }
    }

    fun dismissRecipeEditor() {
        _ui.update { it.copy(recipeEditorCocktail = null) }
    }

    // ── Delete confirm ────────────────────────────────────────────────────────

    fun requestDelete(cocktail: Cocktail) {
        _ui.update { it.copy(deleteConfirmCocktail = cocktail) }
    }

    fun dismissDeleteConfirm() {
        _ui.update { it.copy(deleteConfirmCocktail = null) }
    }

    // ── Misc ──────────────────────────────────────────────────────────────────

    fun dismissToast() = _ui.update { it.copy(toastMessage = null) }
    fun dismissError()  = _ui.update { it.copy(error = null) }
}
