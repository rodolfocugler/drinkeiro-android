package com.drinkeiro.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.drinkeiro.data.model.Cocktail
import com.drinkeiro.data.model.Ingredient
import com.drinkeiro.ui.components.*
import com.drinkeiro.ui.theme.DrinkeiroTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailDetailSheet(
    cocktail:    Cocktail,
    isFav:       Boolean,
    onToggleFav: () -> Unit,
    onBrew:      (Cocktail, List<Ingredient>) -> Unit,
    onEdit:      () -> Unit,
    onDelete:    () -> Unit,
    onDismiss:   () -> Unit,
) {
    val c    = DrinkeiroTheme.colors
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Editable local ingredient copy
    var ings     by remember { mutableStateOf(cocktail.strIngredient.map { it.copy() }) }
    var addingNew by remember { mutableStateOf(false) }
    var newIngName    by remember { mutableStateOf("") }
    var newIngMeasure by remember { mutableStateOf("") }

    // Brew confirm step
    var showBrewConfirm by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState       = sheetState,
        containerColor   = c.bg2,
        dragHandle       = { SheetHandle(modifier = Modifier.padding(top = 12.dp)) },
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // ── Hero image ──────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                if (!cocktail.strDrinkThumb.isNullOrBlank()) {
                    AsyncImage(
                        model             = cocktail.strDrinkThumb,
                        contentDescription = cocktail.strDrink,
                        contentScale      = ContentScale.Crop,
                        modifier          = Modifier.fillMaxSize(),
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize().background(c.bg3),
                        contentAlignment = Alignment.Center,
                    ) { Text("🍸", fontSize = 52.sp) }
                }
                // Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            androidx.compose.ui.graphics.Brush.verticalGradient(
                                listOf(
                                    androidx.compose.ui.graphics.Color.Transparent,
                                    c.bg2.copy(alpha = 0.95f),
                                )
                            )
                        )
                )
                // Name + actions overlay
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.Bottom,
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(cocktail.strDrink, style = MaterialTheme.typography.headlineMedium, color = c.cream)
                        Text(
                            "${cocktail.strGlass} · ${cocktail.strAlcoholic}",
                            style = MaterialTheme.typography.bodySmall,
                            color = c.cream3,
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                        SmallIconBtn(onClick = onDelete, tint = c.error) {
                            Icon(Icons.Outlined.Delete, null, modifier = Modifier.size(17.dp))
                        }
                        SmallIconBtn(onClick = onEdit, tint = c.cream3) {
                            Icon(Icons.Outlined.Edit, null, modifier = Modifier.size(17.dp))
                        }
                        SmallIconBtn(
                            onClick = onToggleFav,
                            tint    = if (isFav) c.accent else c.cream3,
                            bg      = if (isFav) c.accentLo else c.cream4,
                            border  = if (isFav) c.accentMd else c.border,
                        ) {
                            Icon(
                                if (isFav) Icons.Filled.Star else Icons.Outlined.StarOutline,
                                null, modifier = Modifier.size(18.dp),
                            )
                        }
                    }
                }
            }

            // ── Scrollable body ─────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                // Meta chips
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    MetaChip(cocktail.strCategory, c.accentLo, c.accent)
                    cocktail.strIBA?.let { MetaChip("IBA: $it", c.bg3, c.cream3) }
                    cocktail.strTags?.split(",")?.forEach { tag ->
                        MetaChip(tag.trim(), c.bg3, c.cream4)
                    }
                }

                // Instructions
                if (!cocktail.strInstructions.isNullOrBlank()) {
                    Surface(shape = RoundedCornerShape(14.dp), color = c.bg3, border = BorderStroke(1.dp, c.border)) {
                        Column(modifier = Modifier.padding(13.dp)) {
                            SectionLabel("Instructions")
                            Spacer(Modifier.height(4.dp))
                            Text(
                                cocktail.strInstructions,
                                style     = MaterialTheme.typography.bodyMedium,
                                color     = c.cream2,
                                fontStyle = FontStyle.Italic,
                            )
                        }
                    }
                }

                // Ingredients header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically,
                ) {
                    SectionLabel("Ingredients")
                    TextButton(onClick = { addingNew = !addingNew }) {
                        Text(if (addingNew) "Cancel" else "+ Add", color = c.accent, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }

                // Ingredient list
                ings.forEach { ing ->
                    IngredientRow(
                        name    = ing.strIngredient!!,
                        measure = ing.strMeasure ?: "",
                        onMeasureChange = { newVal ->
                            ings = ings.map { if (it.order == ing.order) it.copy(strMeasure = newVal.ifBlank { null }) else it }
                        },
                        onRemove = {
                            ings = ings.filter { it.order != ing.order }
                        },
                    )
                }

                // Add ingredient panel
                if (addingNew) {
                    Surface(
                        shape  = RoundedCornerShape(14.dp),
                        color  = c.accentLo,
                        border = BorderStroke(1.5.dp, c.accentMd),
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            SectionLabel("Add Ingredient")
                            OutlinedTextField(
                                value         = newIngName,
                                onValueChange = { newIngName = it },
                                placeholder   = { Text("Ingredient name…", color = c.cream4) },
                                modifier      = Modifier.fillMaxWidth(),
                                singleLine    = true,
                                shape         = RoundedCornerShape(10.dp),
                                colors        = fieldColors(),
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(
                                    value         = newIngMeasure,
                                    onValueChange = { newIngMeasure = it },
                                    placeholder   = { Text("Measure (e.g. 1.5 oz)", color = c.cream4) },
                                    modifier      = Modifier.weight(1f),
                                    singleLine    = true,
                                    shape         = RoundedCornerShape(10.dp),
                                    colors        = fieldColors(),
                                )
                                Button(
                                    onClick = {
                                        if (newIngName.isNotBlank()) {
                                            val maxOrder = ings.maxOfOrNull { it.order } ?: 0
                                            ings = ings + Ingredient(
                                                strIngredient = newIngName.trim(),
                                                strMeasure    = newIngMeasure.trim().ifBlank { null },
                                                order         = maxOrder + 1,
                                            )
                                            newIngName = ""; newIngMeasure = ""; addingNew = false
                                        }
                                    },
                                    shape  = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = c.accent, contentColor = c.bg0),
                                ) { Text("Add", fontWeight = FontWeight.Bold) }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))
            }

            // ── Brew CTA ────────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(c.bg2)
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                DrinkeiroButton(
                    text     = "🍸  Brew this Cocktail",
                    onClick  = { showBrewConfirm = true },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }

    // ── Brew confirm overlay ────────────────────────────────────────────────
    if (showBrewConfirm) {
        BrewConfirmSheet(
            cocktail  = cocktail,
            finalIngs = ings,
            onConfirm = { onBrew(cocktail, ings); showBrewConfirm = false },
            onDismiss = { showBrewConfirm = false },
        )
    }
}

// ── Brew Confirm Sheet ────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BrewConfirmSheet(
    cocktail:  Cocktail,
    finalIngs: List<Ingredient>,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    val c = DrinkeiroTheme.colors
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState       = sheetState,
        containerColor   = c.bg2,
        dragHandle       = { SheetHandle(modifier = Modifier.padding(top = 12.dp)) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            Text("Ready to brew?", style = MaterialTheme.typography.headlineSmall, color = c.cream)

            // Cocktail preview card
            Surface(shape = RoundedCornerShape(16.dp), color = c.bg3, border = BorderStroke(1.dp, c.border)) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(c.bg2),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (!cocktail.strDrinkThumb.isNullOrBlank()) {
                            AsyncImage(
                                model = cocktail.strDrinkThumb,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize(),
                            )
                        } else {
                            Text("🍸", fontSize = 26.sp)
                        }
                    }
                    Column {
                        Text(cocktail.strDrink, style = MaterialTheme.typography.titleLarge, color = c.cream)
                        Text(cocktail.strGlass, style = MaterialTheme.typography.bodySmall, color = c.cream3)
                    }
                }
            }

            // Ingredient list
            SectionLabel("Ingredients to dispense")
            finalIngs.forEach { ing ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(c.bg3)
                        .border(1.dp, c.border, RoundedCornerShape(10.dp))
                        .padding(horizontal = 12.dp, vertical = 9.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(ing.strIngredient!!, style = MaterialTheme.typography.bodyMedium, color = c.cream2)
                    Text(ing.strMeasure ?: "to fill", style = MaterialTheme.typography.bodySmall, color = c.cream3)
                }
            }

            // Action buttons
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                GhostButton(text = "Cancel", onClick = onDismiss, modifier = Modifier.weight(1f))
                DrinkeiroButton(text = "🍸  Brew Now", onClick = onConfirm, modifier = Modifier.weight(2f))
            }
        }
    }
}

// ── Helpers ───────────────────────────────────────────────────────────────────

@Composable
private fun SmallIconBtn(
    onClick: () -> Unit,
    tint:    androidx.compose.ui.graphics.Color,
    bg:      androidx.compose.ui.graphics.Color = DrinkeiroTheme.colors.cream4,
    border:  androidx.compose.ui.graphics.Color = DrinkeiroTheme.colors.border,
    content: @Composable () -> Unit,
) {
    val c = DrinkeiroTheme.colors
    Surface(
        onClick  = onClick,
        shape    = RoundedCornerShape(10.dp),
        color    = bg,
        border   = BorderStroke(1.5.dp, border),
        modifier = Modifier.size(33.dp),
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            androidx.compose.runtime.CompositionLocalProvider(
                androidx.compose.material3.LocalContentColor provides tint,
            ) { content() }
        }
    }
}

@Composable
private fun MetaChip(label: String, bg: androidx.compose.ui.graphics.Color, fg: androidx.compose.ui.graphics.Color) {
    Surface(shape = RoundedCornerShape(5.dp), color = bg) {
        Text(
            text     = label.uppercase(),
            color    = fg,
            style    = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
        )
    }
}

@Composable
private fun fieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor    = DrinkeiroTheme.colors.accent,
    unfocusedBorderColor  = DrinkeiroTheme.colors.border,
    focusedTextColor      = DrinkeiroTheme.colors.cream,
    unfocusedTextColor    = DrinkeiroTheme.colors.cream,
    cursorColor           = DrinkeiroTheme.colors.accent,
    focusedContainerColor = DrinkeiroTheme.colors.bg0,
    unfocusedContainerColor = DrinkeiroTheme.colors.bg0,
)
