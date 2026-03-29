package com.drinkeiro.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "session")

@Singleton
class SessionRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private object Keys {
        val TOKEN         = stringPreferencesKey("token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val USER_ID       = stringPreferencesKey("user_id")
        val USER_NAME     = stringPreferencesKey("user_name")
        val USER_EMAIL    = stringPreferencesKey("user_email")
        val PHOTO_URL     = stringPreferencesKey("photo_url")
        val MACHINE_ID    = stringPreferencesKey("active_machine_id")
    }

    // ── Logout event broadcast (observed by NavGraph to redirect to Login) ────
    private val _logoutEvent = MutableStateFlow(false)
    val logoutEvent: StateFlow<Boolean> = _logoutEvent.asStateFlow()

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { it[Keys.TOKEN] != null }
    val userName:   Flow<String?> = context.dataStore.data.map { it[Keys.USER_NAME] }
    val userEmail:  Flow<String?> = context.dataStore.data.map { it[Keys.USER_EMAIL] }
    val activeMachineId: Flow<String?> = context.dataStore.data.map { it[Keys.MACHINE_ID] }

    // ── In-memory selected machine ID — shared across all ViewModels ──────────
    private val _activeMachineId = MutableStateFlow<String?>(null)
    val activeMachineIdState: StateFlow<String?> = _activeMachineId.asStateFlow()

    suspend fun init() {
        // Restore persisted machine id into memory on startup
        val saved = context.dataStore.data.firstOrNull()?.get(Keys.MACHINE_ID)
        _activeMachineId.value = saved
    }

    fun setActiveMachineInMemory(machineId: String) {
        _activeMachineId.value = machineId
    }

    suspend fun getToken(): String? =
        context.dataStore.data.firstOrNull()?.get(Keys.TOKEN)

    suspend fun getRefreshToken(): String? =
        context.dataStore.data.firstOrNull()?.get(Keys.REFRESH_TOKEN)

    suspend fun saveSession(
        token:        String,
        refreshToken: String? = null,
        userId:       String,
        name:         String,
        email:        String,
        photoUrl:     String? = null,
    ) {
        context.dataStore.edit { prefs ->
            prefs[Keys.TOKEN]      = token
            prefs[Keys.USER_ID]    = userId
            prefs[Keys.USER_NAME]  = name
            prefs[Keys.USER_EMAIL] = email
            if (refreshToken != null) prefs[Keys.REFRESH_TOKEN] = refreshToken
            if (photoUrl     != null) prefs[Keys.PHOTO_URL]     = photoUrl
        }
        _logoutEvent.value = false
    }

    suspend fun updateToken(token: String) {
        context.dataStore.edit { it[Keys.TOKEN] = token }
    }

    suspend fun setActiveMachine(machineId: String) {
        _activeMachineId.value = machineId
        context.dataStore.edit { it[Keys.MACHINE_ID] = machineId }
    }

    suspend fun logout() {
        context.dataStore.edit { it.clear() }
        _activeMachineId.value = null
        _logoutEvent.value = true
    }
}
