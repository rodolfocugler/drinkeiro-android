package com.drinkeiro.data.api

import android.util.Log
import com.drinkeiro.data.model.RefreshTokenRequest
import com.drinkeiro.data.repository.SessionRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

private const val TAG = "AuthInterceptor"

class AuthInterceptor @Inject constructor(
    private val session: SessionRepository,
    // Lazy to avoid circular dependency with Retrofit
    private val apiProvider: dagger.Lazy<DrinkeiroApi>,
) : Interceptor {

    // Only one coroutine at a time may attempt a token refresh
    private val refreshMutex = Mutex()

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { session.getToken() }
        val response = chain.proceed(withAuth(chain.request(), token))

        if (response.code == 401 || response.code == 403) {
            Log.w(TAG, "Got ${response.code} — attempting token refresh")
            response.close()

            val newToken = runBlocking {
                refreshMutex.withLock {
                    // Another coroutine may have already refreshed — check first
                    val currentToken = session.getToken()
                    if (currentToken != token) {
                        // Already refreshed by another request — use new token
                        currentToken
                    } else {
                        tryRefresh()
                    }
                }
            }

            return if (newToken != null) {
                // Retry original request with new token
                chain.proceed(withAuth(chain.request(), newToken))
            } else {
                // Refresh failed — logout and return original 401
                Log.e(TAG, "Token refresh failed — logging out")
                runBlocking { session.logout() }
                chain.proceed(chain.request())
            }
        }

        return response
    }

    private fun withAuth(request: Request, token: String?): Request =
        if (token != null) request.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        else request

    private suspend fun tryRefresh(): String? {
        val refreshToken = session.getRefreshToken() ?: return null
        return try {
            val response = apiProvider.get().refreshToken(RefreshTokenRequest(refreshToken))
            if (response.isSuccessful) {
                val body = response.body()!!
                session.saveSession(
                    token = body.accessToken,
                    refreshToken = body.refreshToken,
                    userId = body.userId,
                    name = body.name,
                    email = body.email,
                    photoUrl = body.photoUrl
                )
                Log.d(TAG, "Token refreshed successfully")
                body.accessToken
            } else {
                Log.e(TAG, "Refresh endpoint returned ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Refresh request failed: ${e.message}")
            null
        }
    }
}
