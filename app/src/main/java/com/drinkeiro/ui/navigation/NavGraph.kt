package com.drinkeiro.ui.navigation

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.drinkeiro.ui.screens.LoginScreen
import com.drinkeiro.ui.screens.MainScreen
import com.drinkeiro.viewmodel.AuthViewModel
import com.drinkeiro.viewmodel.MachineViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Main  : Screen("main")
}

@Composable
fun DrinkeiroNavGraph(
    onGoogleSignIn:  () -> Unit,
    isLoggedIn:      Boolean,      // passed from MainActivity — already persisted
    logoutEvent:     Boolean,      // broadcast when token refresh fails
) {
    val navController  = rememberNavController()

    // Determine start destination from persisted login state
    val startDest = if (isLoggedIn) Screen.Main.route else Screen.Login.route

    // React to forced logout from AuthInterceptor
    LaunchedEffect(logoutEvent) {
        if (logoutEvent) {
            navController.navigate(Screen.Login.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    NavHost(navController = navController, startDestination = startDest) {
        composable(Screen.Login.route) {
            LoginScreen(
                onGoogleSignIn = onGoogleSignIn,
                onLoginSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
            )
        }
        composable(Screen.Main.route) {
            MainScreen(
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
            )
        }
    }
}
