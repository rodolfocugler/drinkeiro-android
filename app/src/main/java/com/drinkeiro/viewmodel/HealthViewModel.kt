package com.drinkeiro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drinkeiro.data.api.DrinkeiroApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

sealed interface HealthStatus {
    object Checking : HealthStatus   // spinner — actively polling
    object Online   : HealthStatus   // backend responded 200
    object Offline  : HealthStatus   // last attempt failed or timed out
}

@HiltViewModel
class HealthViewModel @Inject constructor(
    private val api: DrinkeiroApi,
) : ViewModel() {

    private val _status  = MutableStateFlow<HealthStatus>(HealthStatus.Checking)
    private val _attempt = MutableStateFlow(0)

    val status  = _status.asStateFlow()
    val attempt = _attempt.asStateFlow()

    private var pollingJob = viewModelScope.launch { poll() }

    /** Called by the UI Retry button */
    fun retry() {
        pollingJob.cancel()
        _status.value  = HealthStatus.Checking
        _attempt.value = 0
        pollingJob = viewModelScope.launch { poll() }
    }

    private suspend fun poll() {
        var isActive = true
        while (isActive) {
            _status.value = HealthStatus.Checking

            // 2-second hard timeout per attempt
            val success = withTimeoutOrNull(2_000L) {
                try {
                    api.health().isSuccessful
                } catch (_: Exception) {
                    false
                }
            } ?: false     // null = timed out → treat as offline

            _attempt.value += 1
            _status.value = if (success) HealthStatus.Online else HealthStatus.Offline
            isActive = !success
            if (success) break          // stop polling once backend is up

            delay(10_000L)              // wait 10 s before next attempt
        }
    }
}
