package com.drinkeiro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drinkeiro.data.model.*
import com.drinkeiro.data.repository.MachineRepository
import com.drinkeiro.data.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MachineUiState(
    val machines:       List<Machine>      = emptyList(),
    val activeMachine:  Machine?           = null,
    val pumps:          List<Pump>         = emptyList(),
    val history:        List<HistoryEntry> = emptyList(),
    val isLoading:      Boolean            = false,
    val isBrewing:      Boolean            = false,
    val toastMessage:   String?            = null,
    val error:          String?            = null,
    // pump test countdown per pump number
    val testingPump:    Int?               = null,
    val testCountdown:  Int                = 10,
)

@HiltViewModel
class MachineViewModel @Inject constructor(
    private val repo:    MachineRepository,
    private val session: SessionRepository,
) : ViewModel() {

    private val _ui = MutableStateFlow(MachineUiState())
    val ui = _ui.asStateFlow()

    private var testJob: Job? = null

    init { loadMachines() }

    // ── Machines ──────────────────────────────────────────────────────────────

    fun loadMachines() {
        viewModelScope.launch {
            _ui.update { it.copy(isLoading = true) }
            repo.getMachines().onSuccess { list ->
                val savedId = session.activeMachineId.firstOrNull()
                val active  = list.firstOrNull { it.id == savedId } ?: list.firstOrNull()
                _ui.update { it.copy(machines = list, activeMachine = active, isLoading = false) }
                active?.let { loadPumps(it.id); loadHistory(it.id) }
            }.onFailure { e ->
                _ui.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun selectMachine(machine: Machine) {
        _ui.update { it.copy(activeMachine = machine) }
        viewModelScope.launch {
            session.setActiveMachine(machine.id)
            loadPumps(machine.id)
            loadHistory(machine.id)
        }
    }

    // ── Brew ──────────────────────────────────────────────────────────────────

    fun brew(cocktail: com.drinkeiro.data.model.Cocktail, ingredients: List<Ingredient>) {
        val machineId = _ui.value.activeMachine?.id ?: return
        viewModelScope.launch {
            _ui.update { it.copy(isBrewing = true) }
            repo.brew(machineId, BrewRequest(cocktail.idDrink, ingredients))
                .onSuccess {
                    loadHistory(machineId)
                    _ui.update { it.copy(isBrewing = false, toastMessage = "Brewing ${cocktail.strDrink}!") }
                }
                .onFailure { e ->
                    _ui.update { it.copy(isBrewing = false, error = e.message) }
                }
        }
    }

    // ── History ───────────────────────────────────────────────────────────────

    fun loadHistory(machineId: String) {
        viewModelScope.launch {
            repo.getHistory(machineId).onSuccess { entries ->
                _ui.update { it.copy(history = entries) }
            }
        }
    }

    // ── Pumps ─────────────────────────────────────────────────────────────────

    fun loadPumps(machineId: String) {
        viewModelScope.launch {
            repo.getPumps(machineId).onSuccess { pumps ->
                _ui.update { it.copy(pumps = pumps) }
            }
        }
    }

    fun createPump(pump: Pump) {
        val machineId = _ui.value.activeMachine?.id ?: return
        viewModelScope.launch {
            repo.createPump(machineId, pump).onSuccess { created ->
                _ui.update { state ->
                    state.copy(
                        pumps        = (state.pumps + created).sortedBy { it.pumpNumber },
                        toastMessage = "Pump ${created.pumpNumber} created",
                    )
                }
            }.onFailure { e -> _ui.update { it.copy(error = e.message) } }
        }
    }

    fun updatePump(pump: Pump) {
        val machineId = _ui.value.activeMachine?.id ?: return
        viewModelScope.launch {
            repo.updatePump(machineId, pump).onSuccess { updated ->
                _ui.update { state ->
                    state.copy(pumps = state.pumps.map { if (it.pumpNumber == updated.pumpNumber) updated else it })
                }
            }.onFailure { e -> _ui.update { it.copy(error = e.message) } }
        }
    }

    fun deletePump(pumpNumber: Int) {
        val machineId = _ui.value.activeMachine?.id ?: return
        viewModelScope.launch {
            repo.deletePump(machineId, pumpNumber).onSuccess {
                _ui.update { state ->
                    state.copy(
                        pumps        = state.pumps.filter { it.pumpNumber != pumpNumber },
                        toastMessage = "Pump $pumpNumber removed",
                    )
                }
            }.onFailure { e -> _ui.update { it.copy(error = e.message) } }
        }
    }

    fun triggerPump(pumpNumber: Int) {
        val machineId = _ui.value.activeMachine?.id ?: return
        testJob?.cancel()
        _ui.update { it.copy(testingPump = pumpNumber, testCountdown = 10) }
        viewModelScope.launch {
            repo.triggerPump(machineId, pumpNumber)
        }
        testJob = viewModelScope.launch {
            for (i in 9 downTo 0) {
                delay(1_000)
                _ui.update { it.copy(testCountdown = i) }
            }
            _ui.update { it.copy(testingPump = null, testCountdown = 10) }
        }
    }

    fun dismissToast() = _ui.update { it.copy(toastMessage = null) }
    fun dismissError() = _ui.update { it.copy(error = null) }
}
