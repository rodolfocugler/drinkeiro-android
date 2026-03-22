package com.drinkeiro.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.drinkeiro.data.model.Cocktail
import com.drinkeiro.ui.theme.DrinkeiroTheme

// ── Section label ─────────────────────────────────────────────────────────────

@Composable
fun SectionLabel(text: String, modifier: Modifier = Modifier) {
    val c = DrinkeiroTheme.colors
    Text(
        text     = text.uppercase(),
        style    = MaterialTheme.typography.labelSmall,
        color    = c.cream3,
        modifier = modifier,
    )
}

// ── Primary button ────────────────────────────────────────────────────────────

@Composable
fun DrinkeiroButton(
    text:     String,
    onClick:  () -> Unit,
    modifier: Modifier = Modifier,
    enabled:  Boolean  = true,
) {
    val c = DrinkeiroTheme.colors
    Button(
        onClick  = onClick,
        enabled  = enabled,
        modifier = modifier.height(52.dp),
        shape    = RoundedCornerShape(16.dp),
        colors   = ButtonDefaults.buttonColors(
            containerColor = c.accent,
            contentColor   = c.bg0,
            disabledContainerColor = c.cream4,
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
    ) {
        Text(text, fontWeight = FontWeight.Bold, fontSize = 15.sp)
    }
}

// ── Ghost button ──────────────────────────────────────────────────────────────

@Composable
fun GhostButton(
    text:     String,
    onClick:  () -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = DrinkeiroTheme.colors
    OutlinedButton(
        onClick  = onClick,
        modifier = modifier.height(52.dp),
        shape    = RoundedCornerShape(16.dp),
        border   = BorderStroke(1.5.dp, c.border),
        colors   = ButtonDefaults.outlinedButtonColors(contentColor = c.cream2),
    ) {
        Text(text, fontWeight = FontWeight.SemiBold)
    }
}

// ── Small amber chip button ───────────────────────────────────────────────────

@Composable
fun AmberChip(
    text:     String,
    onClick:  () -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = DrinkeiroTheme.colors
    Surface(
        onClick   = onClick,
        shape     = RoundedCornerShape(10.dp),
        color     = c.accentLo,
        border    = BorderStroke(1.5.dp, c.accentMd),
        modifier  = modifier,
    ) {
        Text(
            text     = text,
            color    = c.accent,
            style    = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
        )
    }
}

// ── Status dot ────────────────────────────────────────────────────────────────

@Composable
fun StatusDot(online: Boolean, modifier: Modifier = Modifier) {
    val c = DrinkeiroTheme.colors
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue   = 1f,
        targetValue    = 0.3f,
        animationSpec  = infiniteRepeatable(tween(900, easing = LinearEasing), RepeatMode.Reverse),
        label          = "alpha",
    )
    Box(
        modifier = modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(
                if (online) c.green.copy(alpha = if (online) alpha else 1f)
                else Color(0xFF6B7280)
            )
    )
}

// ── Cocktail card ─────────────────────────────────────────────────────────────

@Composable
fun CocktailCard(
    cocktail: Cocktail,
    isFav:    Boolean,
    onClick:  () -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = DrinkeiroTheme.colors
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        // Thumbnail
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(c.bg2)
                .border(1.dp, c.border, RoundedCornerShape(14.dp))
        ) {
            if (!cocktail.strDrinkThumb.isNullOrBlank()) {
                AsyncImage(
                    model             = cocktail.strDrinkThumb,
                    contentDescription = cocktail.strDrink,
                    contentScale      = ContentScale.Crop,
                    modifier          = Modifier.fillMaxSize(),
                )
            } else {
                Text("🍸", modifier = Modifier.align(Alignment.Center), fontSize = 26.sp)
            }
        }

        // Text
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text     = cocktail.strDrink,
                style    = MaterialTheme.typography.titleLarge,
                color    = c.cream,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(3.dp))
            Text(
                text     = cocktail.strIngredient.joinToString(" · ") { it.strIngredient },
                style    = MaterialTheme.typography.bodySmall,
                color    = c.cream3,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(5.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                CategoryChip(cocktail.strCategory.replace("/Unknown", ""))
                if (cocktail.strGlass.isNotBlank()) GlassChip(cocktail.strGlass)
            }
        }

        // Star
        Icon(
            imageVector = if (isFav) Icons.Filled.Star else Icons.Outlined.StarOutline,
            contentDescription = if (isFav) "Remove favorite" else "Add favorite",
            tint     = if (isFav) c.accent else c.cream4,
            modifier = Modifier.size(22.dp),
        )
    }
    HorizontalDivider(color = c.border, thickness = 0.5.dp)
}

@Composable
private fun CategoryChip(label: String) {
    val c = DrinkeiroTheme.colors
    Surface(shape = RoundedCornerShape(5.dp), color = c.accentLo) {
        Text(
            text     = label.uppercase(),
            color    = c.accent,
            style    = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
        )
    }
}

@Composable
private fun GlassChip(label: String) {
    val c = DrinkeiroTheme.colors
    Surface(shape = RoundedCornerShape(5.dp), color = c.bg3, border = BorderStroke(1.dp, c.border)) {
        Text(
            text     = label,
            color    = c.cream4,
            style    = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
        )
    }
}

// ── Bottom sheet handle ───────────────────────────────────────────────────────

@Composable
fun SheetHandle(modifier: Modifier = Modifier) {
    val c = DrinkeiroTheme.colors
    Box(
        modifier = modifier
            .width(40.dp)
            .height(4.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(c.border)
    )
}

// ── Ingredient row (read + edit) ──────────────────────────────────────────────

@Composable
fun IngredientRow(
    name:     String,
    measure:  String,
    onMeasureChange: ((String) -> Unit)? = null,
    onRemove: (() -> Unit)?              = null,
    modifier: Modifier                   = Modifier,
) {
    val c = DrinkeiroTheme.colors
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(c.bg3)
            .border(1.dp, c.border, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text     = name,
            style    = MaterialTheme.typography.bodyMedium,
            color    = c.cream,
            modifier = Modifier.weight(1f),
        )
        if (onMeasureChange != null) {
            BasicTextField(
                value         = measure,
                onValueChange = onMeasureChange,
                textStyle     = MaterialTheme.typography.bodySmall.copy(color = c.cream3),
                modifier      = Modifier.width(80.dp),
                singleLine    = true,
                decorationBox = { inner ->
                    Box(
                        modifier = Modifier
                            .background(c.bg0, RoundedCornerShape(8.dp))
                            .border(1.dp, c.border, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 5.dp)
                    ) { inner() }
                }
            )
        } else {
            Text(measure.ifBlank { "to fill" }, style = MaterialTheme.typography.bodySmall, color = c.cream3)
        }
        if (onRemove != null) {
            IconButton(onClick = onRemove, modifier = Modifier.size(28.dp)) {
                Text("×", color = c.error, fontSize = 18.sp)
            }
        }
    }
}

// ── Input field ───────────────────────────────────────────────────────────────

@Composable
fun DrinkeiroTextField(
    value:        String,
    onValueChange: (String) -> Unit,
    label:        String,
    modifier:     Modifier = Modifier,
    placeholder:  String   = "",
    singleLine:   Boolean  = true,
    minLines:     Int      = 1,
) {
    val c = DrinkeiroTheme.colors
    Column(modifier = modifier) {
        SectionLabel(label)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value         = value,
            onValueChange = onValueChange,
            placeholder   = { Text(placeholder, color = c.cream4) },
            singleLine    = singleLine,
            minLines      = minLines,
            modifier      = Modifier.fillMaxWidth(),
            shape         = RoundedCornerShape(12.dp),
            colors        = OutlinedTextFieldDefaults.colors(
                focusedBorderColor   = c.accent,
                unfocusedBorderColor = c.border,
                focusedTextColor     = c.cream,
                unfocusedTextColor   = c.cream,
                cursorColor          = c.accent,
                focusedContainerColor   = c.bg0,
                unfocusedContainerColor = c.bg0,
            ),
        )
    }
}

// ── Brew toast ────────────────────────────────────────────────────────────────

@Composable
fun BrewToast(message: String, modifier: Modifier = Modifier) {
    val c = DrinkeiroTheme.colors
    Surface(
        modifier  = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape     = RoundedCornerShape(18.dp),
        color     = c.bg2,
        border    = BorderStroke(1.5.dp, c.accentMd),
        shadowElevation = 8.dp,
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Text("🥂", fontSize = 30.sp)
            Column {
                Text("Brewing started!", style = MaterialTheme.typography.titleLarge, color = c.cream)
                Text(message, style = MaterialTheme.typography.bodySmall, color = c.cream3)
            }
        }
    }
}

// Stub for BasicTextField without import collision
@Composable
private fun BasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    textStyle: androidx.compose.ui.text.TextStyle,
    modifier: Modifier,
    singleLine: Boolean,
    decorationBox: @Composable (@Composable () -> Unit) -> Unit,
) {
    androidx.compose.foundation.text.BasicTextField(
        value         = value,
        onValueChange = onValueChange,
        textStyle     = textStyle,
        modifier      = modifier,
        singleLine    = singleLine,
        decorationBox = decorationBox,
    )
}
