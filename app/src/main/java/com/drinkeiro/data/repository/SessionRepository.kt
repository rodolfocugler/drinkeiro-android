package com.drinkeiro.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
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
        val TOKEN      = stringPreferencesKey("token")
        val USER_ID    = stringPreferencesKey("user_id")
        val USER_NAME  = stringPreferencesKey("user_name")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val PHOTO_URL  = stringPreferencesKey("photo_url")
        val MACHINE_ID = stringPreferencesKey("active_machine_id")
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { it[Keys.TOKEN] != null }

    val userName: Flow<String?> = context.dataStore.data
        .map { it[Keys.USER_NAME] }

    val userEmail: Flow<String?> = context.dataStore.data
        .map { it[Keys.USER_EMAIL] }

    val activeMachineId: Flow<String?> = context.dataStore.data
        .map { it[Keys.MACHINE_ID] }

    suspend fun getToken(): String? =
        context.dataStore.data.firstOrNull()?.get(Keys.TOKEN)

    suspend fun saveSession(token: String, userId: String, name: String, email: String, photoUrl: String?) {
        context.dataStore.edit {
            it[Keys.TOKEN]      = token
            it[Keys.USER_ID]    = userId
            it[Keys.USER_NAME]  = name
            it[Keys.USER_EMAIL] = email
            if (photoUrl != null) it[Keys.PHOTO_URL] = photoUrl
        }
    }

    suspend fun setActiveMachine(machineId: String) {
        context.dataStore.edit { it[Keys.MACHINE_ID] = machineId }
    }

    suspend fun clearSession() {
        context.dataStore.edit { it.clear() }
    }
}
