package com.drinkeiro.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ── Colours ───────────────────────────────────────────────────────────────────
// Richer, higher-contrast bar palette

data class DrinkeiroColors(
    // Backgrounds — dark warm brown, not pure black
    val bg0:      Color = Color(0xFF1A1008),   // main background
    val bg1:      Color = Color(0xFF221508),   // slightly lighter
    val bg2:      Color = Color(0xFF2E1E0D),   // cards / sheets
    val bg3:      Color = Color(0xFF3D2A12),   // input fields / rows

    // Borders
    val border:   Color = Color(0xFF5A3D1E),

    // Amber accent — brighter for readability
    val accent:   Color = Color(0xFFD4922E),
    val accentLo: Color = Color(0x33D4922E),
    val accentMd: Color = Color(0x66D4922E),
    val copper:   Color = Color(0xFFB5622A),

    // Text — warm white with clear contrast steps
    val cream:    Color = Color(0xFFFFF3DC),   // primary text — near white
    val cream2:   Color = Color(0xCCFFF3DC),   // 80% — secondary text
    val cream3:   Color = Color(0x99FFF3DC),   // 60% — hints
    val cream4:   Color = Color(0x55FFF3DC),   // 33% — placeholders / dividers

    // Status
    val green:    Color = Color(0xFF6DC88A),
    val red:      Color = Color(0xFFCF4444),
    val error:    Color = Color(0xFFE55C5C),
)

val LocalDrinkeiroColors = staticCompositionLocalOf { DrinkeiroColors() }

// ── Typography ────────────────────────────────────────────────────────────────

val PlayfairFamily = FontFamily.Serif
val CrimsonFamily  = FontFamily.Serif

val DrinkeiroTypography = Typography(
    displayLarge   = TextStyle(fontFamily = PlayfairFamily, fontWeight = FontWeight.Bold,     fontSize = 38.sp, letterSpacing = 0.5.sp),
    headlineLarge  = TextStyle(fontFamily = PlayfairFamily, fontWeight = FontWeight.Bold,     fontSize = 24.sp),
    headlineMedium = TextStyle(fontFamily = PlayfairFamily, fontWeight = FontWeight.SemiBold, fontSize = 20.sp),
    headlineSmall  = TextStyle(fontFamily = PlayfairFamily, fontWeight = FontWeight.SemiBold, fontSize = 17.sp),
    titleLarge     = TextStyle(fontFamily = PlayfairFamily, fontWeight = FontWeight.SemiBold, fontSize = 15.sp),
    bodyLarge      = TextStyle(fontFamily = CrimsonFamily,  fontWeight = FontWeight.Normal,   fontSize = 16.sp, lineHeight = 26.sp),
    bodyMedium     = TextStyle(fontFamily = CrimsonFamily,  fontWeight = FontWeight.Normal,   fontSize = 14.sp, lineHeight = 22.sp),
    bodySmall      = TextStyle(fontFamily = CrimsonFamily,  fontWeight = FontWeight.Normal,   fontSize = 13.sp),
    labelSmall     = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 10.sp, letterSpacing = 1.2.sp),
)

// ── Theme ─────────────────────────────────────────────────────────────────────

object DrinkeiroTheme {
    val colors: DrinkeiroColors
        @Composable @ReadOnlyComposable
        get() = LocalDrinkeiroColors.current
}

@Composable
fun DrinkeiroTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalDrinkeiroColors provides DrinkeiroColors()) {
        MaterialTheme(typography = DrinkeiroTypography, content = content)
    }
}
