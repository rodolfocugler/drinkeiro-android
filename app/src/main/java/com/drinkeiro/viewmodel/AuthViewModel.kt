package com.drinkeiro.viewmodel

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drinkeiro.BuildConfig
import com.drinkeiro.data.api.DrinkeiroApi
import com.drinkeiro.data.model.GoogleAuthRequest
import com.drinkeiro.data.repository.SessionRepository
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "DrinkeiroAuth"

sealed interface AuthState {
    object Idle : AuthState
    object Loading : AuthState
    object Success : AuthState
    data class Error(val message: String) : AuthState
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val api: DrinkeiroApi,
    private val session: SessionRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state = _state.asStateFlow()

    fun signInWithGoogle(context: Context) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            try {
                val credentialManager = CredentialManager.create(context)
                val googleIdOption =
                    GetSignInWithGoogleOption.Builder(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                        .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val result = credentialManager.getCredential(context, request)
                val googleCredential = GoogleIdTokenCredential.createFrom(result.credential.data)
                Log.d(TAG, "Got ID token for ${googleCredential.id}")
                sendTokenToBackend(idToken = googleCredential.idToken)
            } catch (e: GetCredentialException) {
                Log.e(TAG, "GetCredentialException type=${e.type}: ${e.message}")
                _state.value = AuthState.Error("Sign-in failed: ${e.message}")
            } catch (e: Exception) {
                Log.e(TAG, "Unexpected: ${e::class.simpleName}: ${e.message}")
                _state.value = AuthState.Error(e.message ?: "Sign-in failed")
            }
        }
    }

    private suspend fun sendTokenToBackend(idToken: String) {
        try {
            val response = api.loginWithGoogle(GoogleAuthRequest(idToken))
            if (response.isSuccessful) {
                val body = response.body()!!
                session.saveSession(
                    token = body.accessToken,
                    refreshToken = body.refreshToken,
                    userId = body.userId,
                    name = body.name,
                    email = body.email,
                    photoUrl = body.photoUrl,
                )
                _state.value = AuthState.Success
            } else {
                Log.e(TAG, "Backend error ${response.code()}")
                _state.value = AuthState.Error("Backend error ${response.code()}")
            }
        } catch (e: Exception) {
            // Backend unreachable — allow dev login with Google identity
            Log.e(TAG, "Exception: ${e::class.simpleName}: ${e.message}")
            _state.value = AuthState.Error(e.message ?: "Sign-in failed")
        }
    }
}
