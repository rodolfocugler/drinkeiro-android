package com.drinkeiro.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drinkeiro.data.model.Cocktail
import com.drinkeiro.ui.components.*
import com.drinkeiro.ui.theme.DrinkeiroTheme
import com.drinkeiro.viewmodel.CocktailViewModel
import com.drinkeiro.viewmodel.MachineViewModel

private val CATEGORIES = listOf("All", "Ordinary Drink", "Cocktail", "Other/Unknown")

// ── Favorites Tab ─────────────────────────────────────────────────────────────

@Composable
fun FavoritesTab(
    cocktailVm: CocktailViewModel,
    machineVm:  MachineViewModel,
) {
    val ui      by cocktailVm.ui.collectAsState()
    val c       = DrinkeiroTheme.colors
    val favList = ui.cocktails.filter { ui.favorites.contains(it.idDrink) }
    var detail  by remember { mutableStateOf<Cocktail?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        SectionLabel(
            text     = "${favList.size} saved",
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
        )
        if (favList.isEmpty()) {
            EmptyState("No favorites yet.\nTap ☆ on any cocktail to save it.")
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(favList, key = { it.idDrink }) { cocktail ->
                    CocktailCard(
                        cocktail = cocktail,
                        isFav    = true,
                        onClick  = { detail = cocktail },
                    )
                }
            }
        }
    }

    detail?.let { c ->
        CocktailDetailSheet(
            cocktail   = c,
            isFav      = ui.favorites.contains(c.idDrink),
            onToggleFav = { cocktailVm.toggleFavorite(c.idDrink) },
            onBrew     = { finalCocktail, ings -> machineVm.brew(finalCocktail, ings); detail = null },
            onEdit     = { cocktailVm.requestEdit(c); detail = null },
            onDelete   = { cocktailVm.requestDelete(c); detail = null },
            onDismiss  = { detail = null },
        )
    }

    ObserveRecipeDialogs(cocktailVm)
}

// ── Cocktails Tab ─────────────────────────────────────────────────────────────

@Composable
fun CocktailsTab(
    cocktailVm: CocktailViewModel,
    machineVm:  MachineViewModel,
) {
    val ui     by cocktailVm.ui.collectAsState()
    val c      = DrinkeiroTheme.colors
    var detail by remember { mutableStateOf<Cocktail?>(null) }

    val displayed = if (ui.selectedCategory == "All") ui.cocktails
    else ui.cocktails.filter { it.strCategory == ui.selectedCategory }

    Column(modifier = Modifier.fillMaxSize()) {
        // Category filter chips
        LazyRow(
            contentPadding       = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(CATEGORIES) { cat ->
                val selected = cat == ui.selectedCategory
                Surface(
                    onClick = { cocktailVm.setCategory(cat) },
                    shape   = RoundedCornerShape(20.dp),
                    color   = if (selected) c.accentLo else c.bg2,
                    border  = BorderStroke(1.5.dp, if (selected) c.accentMd else c.border),
                ) {
                    Text(
                        text     = cat,
                        color    = if (selected) c.accent else c.cream3,
                        style    = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 7.dp),
                    )
                }
            }
        }

        if (ui.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = c.accent)
            }
        } else if (displayed.isEmpty()) {
            EmptyState("No cocktails in this category.\nTap + Recipe to add one.")
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                modifier       = Modifier.fillMaxSize(),
            ) {
                items(displayed, key = { it.idDrink }) { cocktail ->
                    CocktailCard(
                        cocktail = cocktail,
                        isFav    = ui.favorites.contains(cocktail.idDrink),
                        onClick  = { detail = cocktail },
                    )
                }
            }
        }
    }

    detail?.let { c ->
        CocktailDetailSheet(
            cocktail   = c,
            isFav      = ui.favorites.contains(c.idDrink),
            onToggleFav = { cocktailVm.toggleFavorite(c.idDrink) },
            onBrew     = { finalCocktail, ings -> machineVm.brew(finalCocktail, ings); detail = null },
            onEdit     = { cocktailVm.requestEdit(c); detail = null },
            onDelete   = { cocktailVm.requestDelete(c); detail = null },
            onDismiss  = { detail = null },
        )
    }

    ObserveRecipeDialogs(cocktailVm)
}

// ── History Tab ───────────────────────────────────────────────────────────────

@Composable
fun HistoryTab(
    machineVm:  MachineViewModel,
    cocktailVm: CocktailViewModel,
) {
    val machineUi  by machineVm.ui.collectAsState()
    val cocktailUi by cocktailVm.ui.collectAsState()
    val c          = DrinkeiroTheme.colors
    var detail     by remember { mutableStateOf<Cocktail?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        SectionLabel("Recent Brews", modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp))

        if (machineUi.history.isEmpty()) {
            EmptyState("No brews yet.\nCheers soon! 🥂")
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp),
                modifier       = Modifier.fillMaxSize(),
            ) {
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
                            shape    = RoundedCornerShape(12.dp),
                            color    = c.accentLo,
                            border   = BorderStroke(1.dp, c.accentMd),
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("🍸", fontSize = 22.sp)
                            }
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(entry.strDrink, style = MaterialTheme.typography.titleLarge, color = c.cream)
                            Spacer(Modifier.height(2.dp))
                            Text("${entry.ts} · ${entry.user}", style = MaterialTheme.typography.bodySmall, color = c.cream4)
                        }
                        Surface(
                            onClick = {
                                val cocktail = cocktailUi.cocktails.firstOrNull { it.idDrink == entry.idDrink }
                                if (cocktail != null) detail = cocktail
                            },
                            shape  = RoundedCornerShape(10.dp),
                            color  = c.accentLo,
                            border = BorderStroke(1.dp, c.accentMd),
                        ) {
                            Text(
                                text     = "Again",
                                color    = c.accent,
                                style    = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                            )
                        }
                    }
                    HorizontalDivider(color = c.border, thickness = 0.5.dp)
                }
            }
        }
    }

    detail?.let { cocktail ->
        CocktailDetailSheet(
            cocktail   = cocktail,
            isFav      = cocktailUi.favorites.contains(cocktail.idDrink),
            onToggleFav = { cocktailVm.toggleFavorite(cocktail.idDrink) },
            onBrew     = { fc, ings -> machineVm.brew(fc, ings); detail = null },
            onEdit     = { cocktailVm.requestEdit(cocktail); detail = null },
            onDelete   = { cocktailVm.requestDelete(cocktail); detail = null },
            onDismiss  = { detail = null },
        )
    }

    ObserveRecipeDialogs(cocktailVm)
}

// ── Empty state ───────────────────────────────────────────────────────────────

@Composable
private fun EmptyState(text: String) {
    val c = DrinkeiroTheme.colors
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text      = text,
            style     = MaterialTheme.typography.bodyLarge,
            color     = c.cream4,
            fontStyle = FontStyle.Italic,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier  = Modifier.padding(32.dp),
        )
    }
}

// ── Helper: observe recipe editor / delete dialogs driven by ViewModel ────────

@Composable
fun ObserveRecipeDialogs(vm: CocktailViewModel) {
    val ui by vm.ui.collectAsState()

    ui.recipeEditorCocktail?.let { cocktail ->
        RecipeEditorSheet(
            cocktail  = cocktail,
            isNew     = ui.isCreatingRecipe,
            onSave    = { saved ->
                if (ui.isCreatingRecipe) vm.createCocktail(saved)
                else vm.updateCocktail(saved)
            },
            onDismiss = { vm.dismissRecipeEditor() },
        )
    }

    ui.deleteConfirmCocktail?.let { cocktail ->
        AlertDialog(
            onDismissRequest = { vm.dismissDeleteConfirm() },
            containerColor   = DrinkeiroTheme.colors.bg2,
            title = {
                Text(
                    "Delete \"${cocktail.strDrink}\"?",
                    color = DrinkeiroTheme.colors.cream,
                    style = MaterialTheme.typography.headlineSmall,
                )
            },
            text = {
                Text("This cannot be undone.", color = DrinkeiroTheme.colors.cream3)
            },
            confirmButton = {
                TextButton(onClick = { vm.deleteCocktail(cocktail.idDrink) }) {
                    Text("Delete", color = DrinkeiroTheme.colors.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { vm.dismissDeleteConfirm() }) {
                    Text("Cancel", color = DrinkeiroTheme.colors.cream3)
                }
            },
        )
    }
}
