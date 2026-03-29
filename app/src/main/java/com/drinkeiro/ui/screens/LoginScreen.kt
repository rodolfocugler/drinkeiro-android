package com.drinkeiro.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun LoginScreen(
    onGoogleSignIn: () -> Unit,
    onLoginSuccess: () -> Unit,
    vm: AuthViewModel = hiltViewModel(),
) {
    val state by vm.state.collectAsState()
    val c     = DrinkeiroTheme.colors

    LaunchedEffect(state) {
        if (state is AuthState.Success) onLoginSuccess()
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
                    border          = androidx.compose.foundation.BorderStroke(1.5.dp, c.border),
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

            // ── CTA ───────────────────────────────────────────────────────
            Column(
                modifier            = Modifier.padding(bottom = 48.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val isLoading = state is AuthState.Loading

                // Google Sign-In button
                Surface(
                    onClick         = { if (!isLoading) onGoogleSignIn() },
                    modifier        = Modifier.fillMaxWidth().height(54.dp),
                    shape           = RoundedCornerShape(18.dp),
                    color           = if (isLoading) c.cream4 else c.cream,
                    shadowElevation = if (isLoading) 0.dp else 4.dp,
                ) {
                    Row(
                        modifier              = Modifier.fillMaxSize().padding(horizontal = 24.dp),
                        verticalAlignment     = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        if (isLoading) {
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
                            text       = if (isLoading) "Signing in…" else "Continue with Google",
                            fontWeight = FontWeight.Bold,
                            fontSize   = 15.sp,
                            color      = if (isLoading) c.cream3 else c.bg0,
                        )
                    }
                }

                // Error message
                if (state is AuthState.Error) {
                    Text(
                        text      = (state as AuthState.Error).message,
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

