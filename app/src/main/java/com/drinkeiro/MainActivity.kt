package com.drinkeiro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.drinkeiro.data.repository.SessionRepository
import com.drinkeiro.ui.navigation.DrinkeiroNavGraph
import com.drinkeiro.ui.theme.DrinkeiroTheme
import com.drinkeiro.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    @Inject lateinit var session: SessionRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DrinkeiroTheme {
                val isLoggedIn  by session.isLoggedIn.collectAsState(initial = false)
                val logoutEvent by session.logoutEvent.collectAsState()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color    = DrinkeiroTheme.colors.bg0,
                ) {
                    DrinkeiroNavGraph(
                        // Pass the Activity itself — CredentialManager requires an
                        // Activity context to anchor the sign-in bottom sheet
                        onGoogleSignIn = { authViewModel.signInWithGoogle(this@MainActivity) },
                        isLoggedIn     = isLoggedIn,
                        logoutEvent    = logoutEvent,
                    )
                }
            }
        }
    }
}
