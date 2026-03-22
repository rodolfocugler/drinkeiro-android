package com.drinkeiro.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ── Colours ───────────────────────────────────────────────────────────────────

data class DrinkeiroColors(
    val bg0:      Color = Color(0xFF0F0906),
    val bg1:      Color = Color(0xFF1A1109),
    val bg2:      Color = Color(0xFF251A0E),
    val bg3:      Color = Color(0xFF332515),
    val border:   Color = Color(0xFF3D2C18),
    val accent:   Color = Color(0xFFC47F2A),
    val accentLo: Color = Color(0x26C47F2A),
    val accentMd: Color = Color(0x59C47F2A),
    val copper:   Color = Color(0xFFA0522D),
    val cream:    Color = Color(0xFFF0E6CC),
    val cream2:   Color = Color(0x99F0E6CC),
    val cream3:   Color = Color(0x40F0E6CC),
    val cream4:   Color = Color(0x14F0E6CC),
    val green:    Color = Color(0xFF6AAB7E),
    val red:      Color = Color(0xFFC0392B),
    val error:    Color = Color(0xFFE74C3C),
)

val LocalDrinkeiroColors = staticCompositionLocalOf { DrinkeiroColors() }

// ── Typography ────────────────────────────────────────────────────────────────
// Using system serif fallback; in a real project embed Playfair Display + Crimson Pro fonts
// in app/src/main/res/font/ and reference them here.

val PlayfairFamily = FontFamily.Serif          // swap with Font(R.font.playfair_display_bold)
val CrimsonFamily  = FontFamily.Serif          // swap with Font(R.font.crimson_pro)

val DrinkeiroTypography = Typography(
    displayLarge  = TextStyle(fontFamily = PlayfairFamily, fontWeight = FontWeight.Bold,   fontSize = 38.sp, letterSpacing = 0.5.sp),
    headlineLarge = TextStyle(fontFamily = PlayfairFamily, fontWeight = FontWeight.Bold,   fontSize = 24.sp),
    headlineMedium= TextStyle(fontFamily = PlayfairFamily, fontWeight = FontWeight.SemiBold,fontSize = 20.sp),
    headlineSmall = TextStyle(fontFamily = PlayfairFamily, fontWeight = FontWeight.SemiBold,fontSize = 17.sp),
    titleLarge    = TextStyle(fontFamily = PlayfairFamily, fontWeight = FontWeight.SemiBold,fontSize = 15.sp),
    bodyLarge     = TextStyle(fontFamily = CrimsonFamily,  fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 26.sp),
    bodyMedium    = TextStyle(fontFamily = CrimsonFamily,  fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 22.sp),
    bodySmall     = TextStyle(fontFamily = CrimsonFamily,  fontWeight = FontWeight.Normal, fontSize = 12.sp),
    labelSmall    = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 9.sp, letterSpacing = 1.5.sp),
)

// ── Theme entry point ─────────────────────────────────────────────────────────

object DrinkeiroTheme {
    val colors: DrinkeiroColors
        @Composable @ReadOnlyComposable
        get() = LocalDrinkeiroColors.current
}

@Composable
fun DrinkeiroTheme(content: @Composable () -> Unit) {
    val colors = DrinkeiroColors()
    CompositionLocalProvider(LocalDrinkeiroColors provides colors) {
        MaterialTheme(
            typography = DrinkeiroTypography,
            content    = content,
        )
    }
}
