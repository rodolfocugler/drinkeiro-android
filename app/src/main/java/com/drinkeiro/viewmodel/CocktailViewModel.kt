package com.drinkeiro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drinkeiro.data.model.CocktailDto
import com.drinkeiro.data.model.CocktailEntity
import com.drinkeiro.data.repository.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private var _idCounter = 90000
private fun newId() = "custom_${++_idCounter}"

private fun emptyCocktail() = CocktailDto(
    id = newId(),
    strDrink = "",
    strCategory = "Cocktail",
    strAlcoholic = "Alcoholic",
    strGlass = "",
    isFavorite = false,
    strIngredient = emptyList(),
)

private const val PAGE_SIZE = 20
private const val SEARCH_DELAY = 400L   // ms debounce before sending search to backend

data class CocktailUiState(
    // ── Cocktails tab ─────────────────────────────────────────────────────────
    val cocktails: List<CocktailDto> = emptyList(),
    val categories: List<String> = listOf("All"),
    val selectedCategory: String = "All",
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val isRefreshing: Boolean = false,
    val canLoadMore: Boolean = true,
    val currentPage: Int = 0,

    // ── Favorites tab — own independent list from backend ─────────────────────
    val favorites: List<CocktailDto> = emptyList(),   // full objects, not just ids
    val favSearchQuery: String = "",
    val isFavLoading: Boolean = false,
    val isFavLoadingMore: Boolean = false,
    val isFavRefreshing: Boolean = false,
    val canFavLoadMore: Boolean = true,
    val favCurrentPage: Int = 0,

    // ── Shared ────────────────────────────────────────────────────────────────
    val error: String? = null,
    val toastMessage: String? = null,
    val recipeEditorCocktail: CocktailDto? = null,
    val isCreatingRecipe: Boolean = false,
    val deleteConfirmCocktail: CocktailDto? = null,
) {
    /** Displayed favorites filtered by local search text */
    val favFiltered: List<CocktailDto>
        get() {
            val q = favSearchQuery.trim().lowercase()
            return if (q.isBlank()) favorites
            else favorites.filter { c ->
                c.strDrink.lowercase().contains(q) ||
                        c.strIngredient.any { it.strIngredient.lowercase().contains(q) }
            }
        }
}

@OptIn(FlowPreview::class)
@HiltViewModel
class CocktailViewModel @Inject constructor(
    private val repo: CocktailRepository,
) : ViewModel() {

    private val _ui = MutableStateFlow(CocktailUiState())
    val ui = _ui.asStateFlow()

    // Internal search query flow used to debounce API calls
    private val _searchQuery = MutableStateFlow("")
    private val _favSearchQuery = MutableStateFlow("")

    init {
        loadCocktails(refresh = false)
        loadFavorites(refresh = false)
        loadCategories()

        // Debounce cocktails search → send to backend
        viewModelScope.launch {
            _searchQuery
                .debounce(SEARCH_DELAY)
                .distinctUntilChanged()
                .collectLatest { query ->
                    _ui.update { it.copy(currentPage = 0, canLoadMore = true, isLoading = true) }
                    val cat = _ui.value.selectedCategory.takeIf { it != "All" }
                    repo.getCocktails(
                        category = cat,
                        search = query.ifBlank { null },
                        page = 0,
                        pageSize = PAGE_SIZE
                    )
                        .onSuccess { list ->
                            _ui.update { s ->
                                s.copy(
                                    cocktails = list,
                                    isLoading = false,
                                    canLoadMore = list.size >= PAGE_SIZE,
                                    currentPage = 0
                                )
                            }
                        }
                        .onFailure { _ui.update { it.copy(isLoading = false) } }
                }
        }

        // Debounce favorites search — local only (backend doesn't expose favorite search)
        viewModelScope.launch {
            _favSearchQuery
                .debounce(SEARCH_DELAY)
                .distinctUntilChanged()
                .collect { query ->
                    _ui.update { it.copy(currentPage = 0, canLoadMore = true, isLoading = true, favSearchQuery = query) }
                    repo.getFavorites(
                        search = query.ifBlank { null },
                        page = 0,
                        pageSize = PAGE_SIZE
                    )
                        .onSuccess { list ->
                            _ui.update { s ->
                                s.copy(
                                    favorites = list,
                                    isLoading = false,
                                    canLoadMore = list.size >= PAGE_SIZE,
                                    currentPage = 0
                                )
                            }
                        }
                        .onFailure { _ui.update { it.copy(isLoading = false) } }
                }
        }
    }

    // ── Categories ────────────────────────────────────────────────────────────

    private fun loadCategories() {
        viewModelScope.launch {
            repo.getCategories().onSuccess { cats ->
                _ui.update { it.copy(categories = listOf("All") + cats) }
            }
            // Silently ignore failure — fallback "All" is already in state
        }
    }

    // ── Cocktails load / refresh / loadMore ───────────────────────────────────

    fun loadCocktails(refresh: Boolean = false) {
        if (_ui.value.isLoading || _ui.value.isRefreshing) return
        viewModelScope.launch {
            if (refresh) _ui.update {
                it.copy(
                    isRefreshing = true,
                    currentPage = 0,
                    canLoadMore = true
                )
            }
            else _ui.update { it.copy(isLoading = true, error = null) }

            val cat = _ui.value.selectedCategory.takeIf { it != "All" }
            val query = _ui.value.searchQuery.ifBlank { null }
            repo.getCocktails(category = cat, search = query, page = 0, pageSize = PAGE_SIZE)
                .onSuccess { list ->
                    _ui.update { s ->
                        s.copy(
                            isLoading = false,
                            isRefreshing = false,
                            currentPage = 0,
                            cocktails = list,
                            canLoadMore = list.size >= PAGE_SIZE,
                        )
                    }
                }
                .onFailure { e ->
                    _ui.update {
                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            error = e.message
                        )
                    }
                }
        }
    }

    fun loadMore() {
        val s = _ui.value
        if (!s.canLoadMore || s.isLoadingMore || s.isLoading || s.isRefreshing) return
        val nextPage = s.currentPage + 1
        viewModelScope.launch {
            _ui.update { it.copy(isLoadingMore = true) }
            val cat = s.selectedCategory.takeIf { it != "All" }
            val query = s.searchQuery.ifBlank { null }
            repo.getCocktails(category = cat, search = query, page = nextPage, pageSize = PAGE_SIZE)
                .onSuccess { newItems ->
                    _ui.update { st ->
                        st.copy(
                            isLoadingMore = false,
                            currentPage = nextPage,
                            cocktails = st.cocktails + newItems,
                            canLoadMore = newItems.size >= PAGE_SIZE,
                        )
                    }
                }
                .onFailure { _ui.update { it.copy(isLoadingMore = false) } }
        }
    }

    fun refresh() = loadCocktails(refresh = true)

    // ── Favorites load / refresh / loadMore ───────────────────────────────────

    fun loadFavorites(refresh: Boolean = false) {
        if (_ui.value.isFavLoading || _ui.value.isFavRefreshing) return
        viewModelScope.launch {
            if (refresh) _ui.update {
                it.copy(
                    isFavRefreshing = true,
                    favCurrentPage = 0,
                    canFavLoadMore = true
                )
            }
            else _ui.update { it.copy(isFavLoading = true) }

            repo.getFavorites(
                page = 0,
                search = _searchQuery.value.ifBlank { null },
                pageSize = PAGE_SIZE
            )
                .onSuccess { list ->
                    _ui.update { s ->
                        s.copy(
                            isFavLoading = false,
                            isFavRefreshing = false,
                            favCurrentPage = 0,
                            favorites = list,
                            canFavLoadMore = list.size >= PAGE_SIZE,
                        )
                    }
                }
                .onFailure { e ->
                    _ui.update {
                        it.copy(
                            isFavLoading = false,
                            isFavRefreshing = false,
                            error = e.message
                        )
                    }
                }
        }
    }

    fun loadMoreFavorites() {
        val s = _ui.value
        if (!s.canFavLoadMore || s.isFavLoadingMore || s.isFavLoading || s.isFavRefreshing) return
        val nextPage = s.favCurrentPage + 1
        viewModelScope.launch {
            _ui.update { it.copy(isFavLoadingMore = true) }
            repo.getFavorites(page = nextPage, pageSize = PAGE_SIZE)
                .onSuccess { newItems ->
                    _ui.update { st ->
                        st.copy(
                            isFavLoadingMore = false,
                            favCurrentPage = nextPage,
                            favorites = st.favorites + newItems,
                            canFavLoadMore = newItems.size >= PAGE_SIZE,
                        )
                    }
                }
                .onFailure { _ui.update { it.copy(isFavLoadingMore = false) } }
        }
    }

    fun refreshFavorites() = loadFavorites(refresh = true)

    // ── Search ────────────────────────────────────────────────────────────────

    fun setSearch(query: String) {
        _ui.update { it.copy(searchQuery = query) }
        _searchQuery.value = query   // triggers debounced backend call
    }

    fun setFavSearch(query: String) {
        _ui.update { it.copy(favSearchQuery = query) }
        _favSearchQuery.value = query    // triggers debounced backend call
    }

    // ── Category ──────────────────────────────────────────────────────────────

    fun setCategory(category: String) {
        if (_ui.value.selectedCategory == category) return
        _ui.update {
            it.copy(
                selectedCategory = category,
                searchQuery = "",
                currentPage = 0,
                canLoadMore = true
            )
        }
        _searchQuery.value = ""
        loadCocktails(refresh = false)
    }

    // ── Toggle favorite ───────────────────────────────────────────────────────

    fun toggleFavorite(cocktail: CocktailDto) {
        val isFav = cocktail.isFavorite
        viewModelScope.launch {
            if (isFav) {
                // Optimistic remove
                _ui.update { s -> s.copy(favorites = s.favorites.filter { it.id != cocktail.id }) }
                val result = repo.removeFavorite(cocktail).onSuccess { updated ->
                    _ui.update { s ->
                        s.copy(
                            cocktails = s.cocktails.map { if (it.id == cocktail.id) updated else it },
                        )
                    }
                }
                if (result.isFailure) {
                    // Rollback — reload from backend
                    loadFavorites()
                }
            } else {
                // Add — reload favorites from backend to get the full object
                repo.addFavorite(cocktail).onSuccess { updated ->
                    loadFavorites()
                    _ui.update { s ->
                        s.copy(
                            cocktails = s.cocktails.map { if (it.id == cocktail.id) updated else it },
                        )
                    }
                }
            }
        }
    }

    // ── CRUD ──────────────────────────────────────────────────────────────────

    fun createCocktail(cocktail: CocktailDto) {
        viewModelScope.launch {
            repo.createCocktail(cocktail).onSuccess { created ->
                _ui.update { s ->
                    s.copy(
                        cocktails = listOf(created) + s.cocktails,
                        recipeEditorCocktail = null,
                        toastMessage = "${created.strDrink} created!",
                    )
                }
            }.onFailure { e -> _ui.update { it.copy(error = e.message) } }
        }
    }

    fun updateCocktail(cocktail: CocktailDto) {
        viewModelScope.launch {
            repo.updateCocktail(cocktail).onSuccess { updated ->
                _ui.update { s ->
                    s.copy(
                        cocktails = s.cocktails.map { if (it.id == updated.id) updated else it },
                        // Also update in favorites list if present
                        favorites = s.favorites.map { if (it.id == updated.id) updated else it },
                        recipeEditorCocktail = null,
                        toastMessage = "${updated.strDrink} updated!",
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
                        cocktails = s.cocktails.filter { it.id != idDrink },
                        favorites = s.favorites.filter { it.id != idDrink },
                        deleteConfirmCocktail = null,
                        toastMessage = "Recipe deleted",
                    )
                }
            }.onFailure { e -> _ui.update { it.copy(error = e.message) } }
        }
    }

    // ── Recipe editor ─────────────────────────────────────────────────────────

    fun requestCreateRecipe() =
        _ui.update { it.copy(recipeEditorCocktail = emptyCocktail(), isCreatingRecipe = true) }

    fun requestEdit(cocktail: CocktailDto) =
        _ui.update { it.copy(recipeEditorCocktail = cocktail, isCreatingRecipe = false) }

    fun dismissRecipeEditor() =
        _ui.update { it.copy(recipeEditorCocktail = null) }

    fun requestDelete(cocktail: CocktailDto) =
        _ui.update { it.copy(deleteConfirmCocktail = cocktail) }

    fun dismissDeleteConfirm() =
        _ui.update { it.copy(deleteConfirmCocktail = null) }

    fun dismissToast() = _ui.update { it.copy(toastMessage = null) }
    fun dismissError() = _ui.update { it.copy(error = null) }
}
