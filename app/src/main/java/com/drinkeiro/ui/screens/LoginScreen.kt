package com.drinkeiro.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.drinkeiro.ui.theme.DrinkeiroTheme
import com.drinkeiro.viewmodel.AuthState
import com.drinkeiro.viewmodel.AuthViewModel
import com.drinkeiro.viewmodel.HealthStatus
import com.drinkeiro.viewmodel.HealthViewModel

@Composable
fun LoginScreen(
    onGoogleSignIn: () -> Unit,
    onLoginSuccess: () -> Unit,
    authVm:   AuthViewModel  = hiltViewModel(),
    healthVm: HealthViewModel = hiltViewModel(),
) {
    val authState    by authVm.state.collectAsState()
    val healthStatus by healthVm.status.collectAsState()
    val attempt      by healthVm.attempt.collectAsState()
    val c            = DrinkeiroTheme.colors

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) onLoginSuccess()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(c.bg0, c.bg1, c.bg0)))
    ) {
        // Ambient glow
        Box(
            modifier = Modifier
                .size(280.dp)
                .align(Alignment.TopCenter)
                .offset(y = 160.dp)
                .background(
                    Brush.radialGradient(listOf(c.accent.copy(alpha = 0.12f), c.bg0.copy(alpha = 0f)))
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Spacer(Modifier.height(40.dp))

            // ── Logo ──────────────────────────────────────────────────────
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(18.dp),
            ) {
                Surface(
                    modifier        = Modifier.size(82.dp),
                    shape           = RoundedCornerShape(24.dp),
                    color           = c.bg2,
                    border          = BorderStroke(1.5.dp, c.border),
                    shadowElevation = 12.dp,
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("🍸", fontSize = 40.sp)
                    }
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text          = "Drinkeiro",
                        fontSize      = 38.sp,
                        fontWeight    = FontWeight.Bold,
                        color         = c.cream,
                        letterSpacing = 1.sp,
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text  = "COCKTAIL MACHINE",
                        style = MaterialTheme.typography.labelSmall,
                        color = c.cream3,
                    )
                }
            }

            // ── Illustration ──────────────────────────────────────────────
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🍹", fontSize = 92.sp)
                Spacer(Modifier.height(8.dp))
                Text(
                    text      = "Craft perfection,\none pour at a time.",
                    style     = MaterialTheme.typography.bodyLarge,
                    color     = c.cream3,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic,
                )
            }

            // ── CTA + health indicator ────────────────────────────────────
            Column(
                modifier            = Modifier.padding(bottom = 48.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // ── Backend health banner ─────────────────────────────────
                HealthBanner(
                    status  = healthStatus,
                    attempt = attempt,
                    onRetry = healthVm::retry,
                )

                Spacer(Modifier.height(4.dp))

                val isAuthLoading = authState is AuthState.Loading

                // ── Google sign-in button ─────────────────────────────────
                Surface(
                    onClick         = { if (!isAuthLoading) onGoogleSignIn() },
                    modifier        = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape           = RoundedCornerShape(18.dp),
                    color           = if (isAuthLoading) c.cream4 else c.cream,
                    shadowElevation = if (isAuthLoading) 0.dp else 4.dp,
                ) {
                    Row(
                        modifier              = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp),
                        verticalAlignment     = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        if (isAuthLoading) {
                            CircularProgressIndicator(
                                modifier    = Modifier.size(22.dp),
                                color       = c.accent,
                                strokeWidth = 2.5.dp,
                            )
                        } else {
                            Text(
                                "G",
                                fontSize   = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color      = Color(0xFF4285F4),
                            )
                        }
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text       = if (isAuthLoading) "Signing in…" else "Continue with Google",
                            fontWeight = FontWeight.Bold,
                            fontSize   = 15.sp,
                            color      = if (isAuthLoading) c.cream3 else c.bg0,
                        )
                    }
                }

                // Auth error
                if (authState is AuthState.Error) {
                    Text(
                        text      = (authState as AuthState.Error).message,
                        color     = c.error,
                        style     = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                    )
                }

                Text(
                    text      = "By continuing you agree to our Terms & Privacy Policy",
                    style     = MaterialTheme.typography.labelSmall,
                    color     = c.cream4,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

// ── Health banner composable ──────────────────────────────────────────────────

@Composable
private fun HealthBanner(
    status:  HealthStatus,
    attempt: Int,
    onRetry: () -> Unit,
) {
    val c = DrinkeiroTheme.colors

    val (dotColor, bgColor, borderColor, label) = when (status) {
        HealthStatus.Online   -> Quad(c.green,   c.green.copy(alpha = 0.12f),  c.green.copy(alpha = 0.35f),  "Backend online")
        HealthStatus.Offline  -> Quad(c.error,   c.error.copy(alpha = 0.10f),  c.error.copy(alpha = 0.30f),  if (attempt == 0) "Connecting…" else "Backend unreachable")
        HealthStatus.Checking -> Quad(c.accent,  c.accent.copy(alpha = 0.10f), c.accent.copy(alpha = 0.28f), "Checking backend…")
    }

    Surface(
        shape  = RoundedCornerShape(14.dp),
        color  = bgColor,
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            // Animated status dot
            when (status) {
                HealthStatus.Checking -> PulsingDot(color = dotColor)
                else                  -> StatusDot(color = dotColor)
            }

            // Label
            Text(
                text     = label,
                style    = MaterialTheme.typography.bodySmall,
                color    = dotColor,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.SemiBold,
            )

            // Attempt counter (only shown when failing)
            if (status == HealthStatus.Offline && attempt > 0) {
                Text(
                    text  = "Attempt $attempt · retrying in 10s",
                    style = MaterialTheme.typography.labelSmall,
                    color = c.cream3,
                )
            }

            // Retry button
            if (status == HealthStatus.Offline) {
                TextButton(
                    onClick      = onRetry,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                ) {
                    Text(
                        "Retry",
                        color      = c.accent,
                        fontWeight = FontWeight.Bold,
                        fontSize   = 12.sp,
                    )
                }
            }
        }
    }
}

// ── Dot helpers ───────────────────────────────────────────────────────────────

@Composable
private fun StatusDot(color: Color) {
    Box(
        modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(color)
    )
}

@Composable
private fun PulsingDot(color: Color) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue  = 1f,
        targetValue   = 0.25f,
        animationSpec = infiniteRepeatable(
            animation  = tween(700, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "pulseAlpha",
    )
    Box(
        modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = alpha))
    )
}

// Tiny data holder to destructure banner props cleanly
private data class Quad<A, B, C, D>(val a: A, val b: B, val c: C, val d: D)

private operator fun <A, B, C, D> Quad<A, B, C, D>.component1() = a
private operator fun <A, B, C, D> Quad<A, B, C, D>.component2() = b
private operator fun <A, B, C, D> Quad<A, B, C, D>.component3() = c
private operator fun <A, B, C, D> Quad<A, B, C, D>.component4() = d
