@file:OptIn(ExperimentalMaterial3Api::class)

package com.drinkeiro.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drinkeiro.data.model.Cocktail
import com.drinkeiro.data.model.Ingredient
import com.drinkeiro.ui.components.*
import com.drinkeiro.ui.theme.DrinkeiroTheme

private val CATEGORIES   = listOf("Ordinary Drink", "Cocktail", "Shot", "Coffee / Tea", "Punch / Party Drink", "Soft Drink", "Other/Unknown")
private val ALCOHOLIC    = listOf("Alcoholic", "Non alcoholic", "Optional alcohol")
private val CC_OPTIONS   = listOf("Yes", "No")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeEditorSheet(
    cocktail:  Cocktail,
    isNew:     Boolean,
    onSave:    (Cocktail) -> Unit,
    onDismiss: () -> Unit,
) {
    val c          = DrinkeiroTheme.colors
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // ── Local form state – mirrors the JSON entity fields ────────────────────
    var strDrink                  by remember { mutableStateOf(cocktail.strDrink) }
    var strDrinkAlternate         by remember { mutableStateOf(cocktail.strDrinkAlternate ?: "") }
    var strCategory               by remember { mutableStateOf(cocktail.strCategory) }
    var strAlcoholic              by remember { mutableStateOf(cocktail.strAlcoholic) }
    var strGlass                  by remember { mutableStateOf(cocktail.strGlass) }
    var strIBA                    by remember { mutableStateOf(cocktail.strIBA ?: "") }
    var strTags                   by remember { mutableStateOf(cocktail.strTags ?: "") }
    var strInstructions           by remember { mutableStateOf(cocktail.strInstructions ?: "") }
    var strDrinkThumb             by remember { mutableStateOf(cocktail.strDrinkThumb ?: "") }
    var strCreativeCommons        by remember { mutableStateOf(cocktail.strCreativeCommonsConfirmed ?: "No") }
    var ings                      by remember { mutableStateOf(cocktail.strIngredient.map { it.copy() }) }

    // New ingredient input
    var newIngName    by remember { mutableStateOf("") }
    var newIngMeasure by remember { mutableStateOf("") }

    val canSave = strDrink.isNotBlank()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState       = sheetState,
        containerColor   = c.bg2,
        dragHandle       = { SheetHandle(modifier = Modifier.padding(top = 12.dp)) },
    ) {
        // ── Sheet header ───────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically,
        ) {
            Text(
                if (isNew) "New Recipe" else "Edit Recipe",
                style = MaterialTheme.typography.headlineSmall,
                color = c.cream,
            )
            IconButton(onClick = onDismiss) {
                Text("×", color = c.cream3, fontSize = 24.sp)
            }
        }
        HorizontalDivider(color = c.border, thickness = 0.5.dp)

        // ── Scrollable form body ───────────────────────────────────────────
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {

            // strDrink *
            DrinkeiroTextField(
                value         = strDrink,
                onValueChange = { strDrink = it },
                label         = "Cocktail Name *",
                placeholder   = "e.g. Tropical Sunset",
            )

            // strDrinkAlternate
            DrinkeiroTextField(
                value         = strDrinkAlternate,
                onValueChange = { strDrinkAlternate = it },
                label         = "Alternate Name",
                placeholder   = "Optional",
            )

            // strCategory + strAlcoholic row
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    SectionLabel("Category")
                    Spacer(Modifier.height(6.dp))
                    DropdownField(
                        value    = strCategory,
                        options  = CATEGORIES,
                        onSelect = { strCategory = it },
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    SectionLabel("Alcoholic")
                    Spacer(Modifier.height(6.dp))
                    DropdownField(
                        value    = strAlcoholic,
                        options  = ALCOHOLIC,
                        onSelect = { strAlcoholic = it },
                    )
                }
            }

            // strGlass
            DrinkeiroTextField(
                value         = strGlass,
                onValueChange = { strGlass = it },
                label         = "Glass",
                placeholder   = "e.g. Collins Glass",
            )

            // strIBA + strTags row
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                DrinkeiroTextField(
                    value         = strIBA,
                    onValueChange = { strIBA = it },
                    label         = "IBA Category",
                    placeholder   = "e.g. Unforgettables",
                    modifier      = Modifier.weight(1f),
                )
                DrinkeiroTextField(
                    value         = strTags,
                    onValueChange = { strTags = it },
                    label         = "Tags",
                    placeholder   = "IBA, Party",
                    modifier      = Modifier.weight(1f),
                )
            }

            // strInstructions
            DrinkeiroTextField(
                value         = strInstructions,
                onValueChange = { strInstructions = it },
                label         = "Instructions",
                placeholder   = "Preparation instructions…",
                singleLine    = false,
                minLines      = 3,
            )

            // strDrinkThumb
            DrinkeiroTextField(
                value         = strDrinkThumb,
                onValueChange = { strDrinkThumb = it },
                label         = "Image URL",
                placeholder   = "https://…",
            )

            // strCreativeCommonsConfirmed toggle
            Column {
                SectionLabel("Creative Commons")
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CC_OPTIONS.forEach { opt ->
                        val selected = strCreativeCommons == opt
                        Surface(
                            onClick = { strCreativeCommons = opt },
                            shape   = RoundedCornerShape(10.dp),
                            color   = if (selected) c.accentLo else c.bg3,
                            border  = BorderStroke(1.5.dp, if (selected) c.accentMd else c.border),
                            modifier = Modifier.weight(1f),
                        ) {
                            Text(
                                text       = opt,
                                color      = if (selected) c.accent else c.cream3,
                                fontWeight = FontWeight.SemiBold,
                                fontSize   = 13.sp,
                                modifier   = Modifier.padding(vertical = 10.dp).fillMaxWidth().wrapContentWidth(),
                            )
                        }
                    }
                }
            }

            // ── Ingredients ────────────────────────────────────────────────
            SectionLabel("Ingredients")

            if (ings.isEmpty()) {
                Surface(
                    shape  = RoundedCornerShape(12.dp),
                    color  = c.bg3,
                    border = BorderStroke(1.dp, c.border),
                ) {
                    Text(
                        text     = "No ingredients yet.",
                        color    = c.cream4,
                        style    = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(14.dp).fillMaxWidth().wrapContentWidth(),
                    )
                }
            }

            ings.forEach { ing ->
                Surface(
                    shape   = RoundedCornerShape(12.dp),
                    color   = c.bg3,
                    border  = BorderStroke(1.dp, c.border),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        // strIngredient editable
                        androidx.compose.foundation.text.BasicTextField(
                            value         = ing.strIngredient,
                            onValueChange = { v ->
                                ings = ings.map { if (it.order == ing.order) it.copy(strIngredient = v) else it }
                            },
                            singleLine    = true,
                            textStyle     = MaterialTheme.typography.bodyMedium.copy(color = c.cream),
                            modifier      = Modifier.fillMaxWidth(),
                        )
                        HorizontalDivider(color = c.border, modifier = Modifier.padding(vertical = 4.dp))
                        // strMeasure editable
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment     = Alignment.CenterVertically,
                            modifier              = Modifier.fillMaxWidth(),
                        ) {
                            androidx.compose.foundation.text.BasicTextField(
                                value         = ing.strMeasure ?: "",
                                onValueChange = { v ->
                                    ings = ings.map { if (it.order == ing.order) it.copy(strMeasure = v.ifBlank { null }) else it }
                                },
                                singleLine    = true,
                                textStyle     = MaterialTheme.typography.bodySmall.copy(color = c.cream3),
                                modifier      = Modifier.weight(1f),
                                decorationBox = { inner ->
                                    if ((ing.strMeasure ?: "").isEmpty()) {
                                        Text("Measure (e.g. 1.5 oz)", color = c.cream4, style = MaterialTheme.typography.bodySmall)
                                    }
                                    inner()
                                },
                            )
                            TextButton(
                                onClick = { ings = ings.filter { it.order != ing.order } },
                                colors  = ButtonDefaults.textButtonColors(contentColor = c.error),
                            ) { Text("×", fontSize = 18.sp) }
                        }
                    }
                }
            }

            // Add ingredient
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
                        singleLine    = true,
                        shape         = RoundedCornerShape(10.dp),
                        modifier      = Modifier.fillMaxWidth(),
                        colors        = editorFieldColors(),
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value         = newIngMeasure,
                            onValueChange = { newIngMeasure = it },
                            placeholder   = { Text("e.g. 2 oz", color = c.cream4) },
                            singleLine    = true,
                            shape         = RoundedCornerShape(10.dp),
                            modifier      = Modifier.weight(1f),
                            colors        = editorFieldColors(),
                        )
                        Button(
                            onClick = {
                                if (newIngName.isNotBlank()) {
                                    val max = ings.maxOfOrNull { it.order } ?: 0
                                    ings = ings + Ingredient(
                                        strIngredient = newIngName.trim(),
                                        strMeasure    = newIngMeasure.trim().ifBlank { null },
                                        order         = max + 1,
                                    )
                                    newIngName = ""; newIngMeasure = ""
                                }
                            },
                            shape  = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = c.accent, contentColor = c.bg0),
                        ) { Text("Add", fontWeight = FontWeight.Bold) }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
        }

        // ── Footer buttons ─────────────────────────────────────────────────
        HorizontalDivider(color = c.border, thickness = 0.5.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 22.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            GhostButton(text = "Cancel", onClick = onDismiss, modifier = Modifier.weight(1f))
            DrinkeiroButton(
                text     = if (isNew) "Create Recipe" else "Save Changes",
                onClick  = {
                    if (canSave) {
                        onSave(
                            cocktail.copy(
                                strDrink                   = strDrink.trim(),
                                strDrinkAlternate          = strDrinkAlternate.trim().ifBlank { null },
                                strCategory                = strCategory,
                                strAlcoholic               = strAlcoholic,
                                strGlass                   = strGlass.trim(),
                                strIBA                     = strIBA.trim().ifBlank { null },
                                strTags                    = strTags.trim().ifBlank { null },
                                strInstructions            = strInstructions.trim(),
                                strDrinkThumb              = strDrinkThumb.trim().ifBlank { null },
                                strCreativeCommonsConfirmed = strCreativeCommons,
                                strIngredient              = ings,
                                dateModified               = java.time.LocalDateTime.now().toString().substring(0, 19).replace('T', ' '),
                            )
                        )
                    }
                },
                enabled  = canSave,
                modifier = Modifier.weight(2f),
            )
        }
    }
}

// ── Dropdown field ────────────────────────────────────────────────────────────

@Composable
private fun DropdownField(
    value:    String,
    options:  List<String>,
    onSelect: (String) -> Unit,
) {
    val c       = DrinkeiroTheme.colors
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
        OutlinedTextField(
            value         = value,
            onValueChange = {},
            readOnly      = true,
            trailingIcon  = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            singleLine    = true,
            shape         = RoundedCornerShape(12.dp),
            colors        = editorFieldColors(),
            modifier      = Modifier.fillMaxWidth().menuAnchor(),
        )
        ExposedDropdownMenu(
            expanded         = expanded,
            onDismissRequest = { expanded = false },
            containerColor   = c.bg2,
        ) {
            options.forEach { opt ->
                DropdownMenuItem(
                    text    = { Text(opt, color = c.cream) },
                    onClick = { onSelect(opt); expanded = false },
                )
            }
        }
    }
}

@Composable
private fun editorFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor      = DrinkeiroTheme.colors.accent,
    unfocusedBorderColor    = DrinkeiroTheme.colors.border,
    focusedTextColor        = DrinkeiroTheme.colors.cream,
    unfocusedTextColor      = DrinkeiroTheme.colors.cream,
    cursorColor             = DrinkeiroTheme.colors.accent,
    focusedContainerColor   = DrinkeiroTheme.colors.bg0,
    unfocusedContainerColor = DrinkeiroTheme.colors.bg0,
)
