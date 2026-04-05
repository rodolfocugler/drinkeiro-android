package com.drinkeiro.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drinkeiro.data.model.CocktailDto
import com.drinkeiro.ui.components.*
import com.drinkeiro.ui.theme.DrinkeiroTheme
import com.drinkeiro.viewmodel.CocktailViewModel
import com.drinkeiro.viewmodel.MachineViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// ── Search bar ────────────────────────────────────────────────────────────────

@Composable
private fun SearchBar(
    query: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = DrinkeiroTheme.colors
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(c.bg3)
            .border(1.dp, c.border, RoundedCornerShape(14.dp))
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            Icons.Default.Search,
            contentDescription = null,
            tint = c.cream3,
            modifier = Modifier.size(18.dp),
        )
        Box(modifier = Modifier.weight(1f)) {
            if (query.isEmpty()) {
                Text(
                    "Search cocktails or ingredients…",
                    style = MaterialTheme.typography.bodyMedium,
                    color = c.cream3,
                )
            }
            BasicTextField(
                value = query,
                onValueChange = onChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = c.cream),
                cursorBrush = SolidColor(c.accent),
                modifier = Modifier.fillMaxWidth(),
            )
        }
        if (query.isNotEmpty()) {
            IconButton(onClick = { onChange("") }, modifier = Modifier.size(20.dp)) {
                Icon(Icons.Default.Clear, null, tint = c.cream3, modifier = Modifier.size(16.dp))
            }
        }
    }
}

// ── Load-more trigger ─────────────────────────────────────────────────────────

@Composable
private fun InfiniteScrollHandler(
    listState: androidx.compose.foundation.lazy.LazyListState,
    buffer: Int = 3,
    onLoadMore: () -> Unit,
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val total = listState.layoutInfo.totalItemsCount
            total > 0 && lastVisible >= total - buffer
        }
    }
    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) onLoadMore()
    }
}

// ── Loading footer ────────────────────────────────────────────────────────────

@Composable
private fun LoadingFooter() {
    val c = DrinkeiroTheme.colors
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            color = c.accent,
            strokeWidth = 2.dp
        )
    }
}

// ── Favorites Tab ─────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesTab(
    cocktailVm: CocktailViewModel,
    machineVm: MachineViewModel,
) {
    val ui by cocktailVm.ui.collectAsState()
    val c = DrinkeiroTheme.colors
    val listState = rememberLazyListState()
    var detail by remember { mutableStateOf<CocktailDto?>(null) }

    InfiniteScrollHandler(listState = listState, onLoadMore = cocktailVm::loadMoreFavorites)

    PullToRefreshBox(
        isRefreshing = ui.isFavRefreshing,
        onRefresh = cocktailVm::refreshFavorites,
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            // Search bar (local filter only)
            item {
                SearchBar(
                    query = ui.favSearchQuery,
                    onChange = cocktailVm::setFavSearch,
                    modifier = Modifier.padding(vertical = 12.dp),
                )
            }

            item {
                SectionLabel(
                    text = "${ui.favFiltered.size} saved",
                    modifier = Modifier.padding(bottom = 8.dp),
                )
            }

            if (ui.isFavLoading) {
                item {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = c.accent)
                    }
                }
            } else if (ui.favFiltered.isEmpty()) {
                item {
                    EmptyState(
                        if (ui.favSearchQuery.isNotBlank()) "No favorites match \"${ui.favSearchQuery}\""
                        else "No favorites yet.\nTap ☆ on any cocktail to save it."
                    )
                }
            } else {
                items(ui.favFiltered, key = { it.id }) { cocktail ->
                    CocktailCard(
                        cocktail = cocktail,
                        isFav = true,
                        onClick = { detail = cocktail },
                    )
                }
            }

            if (ui.isFavLoadingMore) item { LoadingFooter() }
        }
    }

    detail?.let { ckt ->
        CocktailDetailSheet(
            cocktail = ckt,
            isFav = ui.cocktails.firstOrNull { it.id == ckt.id }?.isFavorite ?: false,
            onToggleFav = { cocktailVm.toggleFavorite(ckt) },
            onBrew = { fc, ings -> machineVm.brew(fc, ings); detail = null },
            onEdit = { cocktailVm.requestEdit(ckt); detail = null },
            onDelete = { cocktailVm.requestDelete(ckt); detail = null },
            onDismiss = { detail = null },
        )
    }

    ObserveRecipeDialogs(cocktailVm)
}

// ── Cocktails Tab ─────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailsTab(
    cocktailVm: CocktailViewModel,
    machineVm: MachineViewModel,
) {
    val ui by cocktailVm.ui.collectAsState()
    val c = DrinkeiroTheme.colors
    val listState = rememberLazyListState()
    var detail by remember { mutableStateOf<CocktailDto?>(null) }

    InfiniteScrollHandler(listState = listState, onLoadMore = cocktailVm::loadMore)

    PullToRefreshBox(
        isRefreshing = ui.isRefreshing,
        onRefresh = cocktailVm::refresh,
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            // Search bar
            item {
                SearchBar(
                    query = ui.searchQuery,
                    onChange = cocktailVm::setSearch,
                    modifier = Modifier.padding(vertical = 12.dp),
                )
            }

            // Category chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 8.dp),
                ) {
                    items(ui.categories) { cat ->
                        val selected = cat == ui.selectedCategory
                        Surface(
                            onClick = { cocktailVm.setCategory(cat) },
                            shape = RoundedCornerShape(20.dp),
                            color = if (selected) c.accentLo else c.bg2,
                            border = BorderStroke(1.5.dp, if (selected) c.accentMd else c.border),
                        ) {
                            Text(
                                text = cat,
                                color = if (selected) c.accent else c.cream2,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 7.dp),
                            )
                        }
                    }
                }
            }

            // Loading state
            if (ui.isLoading) {
                item {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = c.accent)
                    }
                }
            } else if (ui.cocktails.isEmpty()) {
                item {
                    EmptyState(
                        if (ui.searchQuery.isNotBlank()) "No cocktails match \"${ui.searchQuery}\""
                        else "No cocktails here.\nTap + Recipe to add one."
                    )
                }
            } else {
                items(ui.cocktails, key = { it.id }) { cocktail ->
                    CocktailCard(
                        cocktail = cocktail,
                        isFav = cocktail.isFavorite,
                        onClick = { detail = cocktail },
                    )
                }
            }

            if (ui.isLoadingMore) item { LoadingFooter() }
        }
    }

    detail?.let { ckt ->
        CocktailDetailSheet(
            cocktail = ckt,
            isFav = ui.cocktails.firstOrNull { it.id == ckt.id }?.isFavorite ?: false,
            onToggleFav = { cocktailVm.toggleFavorite(ckt) },
            onBrew = { fc, ings -> machineVm.brew(fc, ings); detail = null },
            onEdit = { cocktailVm.requestEdit(ckt); detail = null },
            onDelete = { cocktailVm.requestDelete(ckt); detail = null },
            onDismiss = { detail = null },
        )
    }

    ObserveRecipeDialogs(cocktailVm)
}

// ── History Tab ───────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryTab(
    machineVm: MachineViewModel,
    cocktailVm: CocktailViewModel,
) {
    val machineUi by machineVm.ui.collectAsState()
    val cocktailUi by cocktailVm.ui.collectAsState()
    val c = DrinkeiroTheme.colors
    var detail by remember { mutableStateOf<CocktailDto?>(null) }
    val listState = rememberLazyListState()

    InfiniteScrollHandler(listState = listState, onLoadMore = machineVm::loadMoreHistory)

    PullToRefreshBox(
        isRefreshing = machineUi.isHistoryRefresh,
        onRefresh = machineVm::refreshHistory,
        modifier = Modifier.fillMaxSize(),
    ) {
        if (machineUi.isHistoryLoading && machineUi.history.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = c.accent)
            }
        } else if (machineUi.history.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                EmptyState("No brews yet.\nCheers soon! 🥂")
            }
        } else {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(horizontal = 20.dp),
                modifier = Modifier.fillMaxSize(),
            ) {
                item {
                    SectionLabel("Recent Brews", modifier = Modifier.padding(vertical = 12.dp))
                }
                items(machineUi.history, key = { it.id }) { entry ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Surface(
                            modifier = Modifier.size(44.dp),
                            shape = RoundedCornerShape(12.dp),
                            color = c.accentLo,
                            border = BorderStroke(1.dp, c.accentMd),
                        ) {
                            Box(contentAlignment = Alignment.Center) { Text("🍸", fontSize = 22.sp) }
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault()).format(
                                    Date(entry.timestamp)
                                ),
                                style = MaterialTheme.typography.titleLarge, color = c.cream
                            )
                            Spacer(Modifier.height(2.dp))
                            Text(
                                entry.historyEntries.joinToString(", ") { it.name },
                                style = MaterialTheme.typography.bodySmall,
                                color = c.cream3
                            )
                        }
                    }
                    HorizontalDivider(color = c.border, thickness = 0.5.dp)
                }
                if (machineUi.isHistoryLoadMore) {
                    item {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = c.accent,
                                strokeWidth = 2.dp
                            )
                        }
                    }
                }
            }
        }
    }

    detail?.let { cocktail ->
        CocktailDetailSheet(
            cocktail = cocktail,
            isFav = cocktailUi.cocktails.firstOrNull { it.id == cocktail.id }?.isFavorite ?: false,
            onToggleFav = { cocktailVm.toggleFavorite(cocktail) },
            onBrew = { fc, ings -> machineVm.brew(fc, ings); detail = null },
            onEdit = { cocktailVm.requestEdit(cocktail); detail = null },
            onDelete = { cocktailVm.requestDelete(cocktail); detail = null },
            onDismiss = { detail = null },
        )
    }

    ObserveRecipeDialogs(cocktailVm)
}

// ── Empty state ───────────────────────────────────────────────────────────────

@Composable
fun EmptyState(text: String) {
    val c = DrinkeiroTheme.colors
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 60.dp, horizontal = 32.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = c.cream3,
            fontStyle = FontStyle.Italic,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )
    }
}

// ── Recipe dialogs (editor + delete confirm) ──────────────────────────────────

@Composable
fun ObserveRecipeDialogs(vm: CocktailViewModel) {
    val ui by vm.ui.collectAsState()

    ui.recipeEditorCocktail?.let { cocktail ->
        RecipeEditorSheet(
            cocktail = cocktail,
            categories = ui.categories,
            isNew = ui.isCreatingRecipe,
            onSave = { saved ->
                if (ui.isCreatingRecipe) vm.createCocktail(saved) else vm.updateCocktail(saved)
            },
            onDismiss = { vm.dismissRecipeEditor() },
        )
    }

    ui.deleteConfirmCocktail?.let { cocktail ->
        AlertDialog(
            onDismissRequest = { vm.dismissDeleteConfirm() },
            containerColor = DrinkeiroTheme.colors.bg2,
            title = {
                Text(
                    "Delete \"${cocktail.strDrink}\"?",
                    color = DrinkeiroTheme.colors.cream,
                    style = MaterialTheme.typography.headlineSmall,
                )
            },
            text = {
                Text("This cannot be undone.", color = DrinkeiroTheme.colors.cream2)
            },
            confirmButton = {
                TextButton(onClick = { vm.deleteCocktail(cocktail.id) }) {
                    Text(
                        "Delete",
                        color = DrinkeiroTheme.colors.error,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { vm.dismissDeleteConfirm() }) {
                    Text("Cancel", color = DrinkeiroTheme.colors.cream2)
                }
            },
        )
    }
}
