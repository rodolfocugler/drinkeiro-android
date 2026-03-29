package com.drinkeiro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drinkeiro.data.api.DrinkeiroApi
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
    val machines: List<Machine> = emptyList(),
    val activeMachine: Machine? = null,
    val pumps: List<Pump> = emptyList(),
    val history: List<HistoryEntry> = emptyList(),
    val isLoading: Boolean = false,
    val isBrewing: Boolean = false,
    val toastMessage: String? = null,
    val error: String? = null,
    val testingPump: Int? = null,
    val testCountdown: Int = 10,
    // Collaborator management
    val showCollaborators: Boolean = false,
    // New machine dialog
    val showCreateMachine: Boolean = false,
)

@HiltViewModel
class MachineViewModel @Inject constructor(
    private val machineRepo: MachineRepository,
    private val api: DrinkeiroApi,
    private val session: SessionRepository,
) : ViewModel() {

    private val _ui = MutableStateFlow(MachineUiState())
    val ui = _ui.asStateFlow()

    // Expose the active machine ID as a StateFlow for all screens
    val activeMachineId: StateFlow<String?> = session.activeMachineIdState

    private var testJob: Job? = null

    init {
        viewModelScope.launch { session.init() }
        loadMachines()
    }

    // ── Machines ──────────────────────────────────────────────────────────────

    fun loadMachines() {
        viewModelScope.launch {
            _ui.update { it.copy(isLoading = true) }
            machineRepo.getMachines().onSuccess { list ->
                val savedId = session.activeMachineIdState.value
                    ?: session.activeMachineId.firstOrNull()
                val active = list.firstOrNull { it.id == savedId } ?: list.firstOrNull()
                _ui.update { it.copy(machines = list, activeMachine = active, isLoading = false) }
                if (active != null) {
                    session.setActiveMachineInMemory(active.id)
                    loadPumps(active.id)
                    loadHistory(active.id)
                }
            }.onFailure { e ->
                _ui.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun selectMachine(machine: Machine) {
        _ui.update { it.copy(activeMachine = machine) }
        viewModelScope.launch {
            session.setActiveMachine(machine.id)   // persists + updates in-memory state
            loadPumps(machine.id)
            loadHistory(machine.id)
        }
    }

    fun createMachine(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            val newMachine = Machine(
                id = "",          // backend assigns the real id
                name = name.trim(),
                status = "offline",
                collaborators = emptyList(),
            )
            machineRepo.createMachine(newMachine).onSuccess { created ->
                _ui.update { s ->
                    s.copy(
                        machines = s.machines + created,
                        showCreateMachine = false,
                        toastMessage = "\"${created.name}\" created",
                    )
                }
            }.onFailure { e ->
                _ui.update { it.copy(error = e.message) }
            }
        }
    }

    fun deleteMachine(machineId: String) {
        viewModelScope.launch {
            machineRepo.deleteMachine(machineId).onSuccess {
                _ui.update { s ->
                    val remaining = s.machines.filter { it.id != machineId }
                    val newActive =
                        if (s.activeMachine?.id == machineId) remaining.firstOrNull() else s.activeMachine
                    if (newActive != null) session.setActiveMachine(newActive.id)
                    s.copy(machines = remaining, activeMachine = newActive)
                }
            }.onFailure { e -> _ui.update { it.copy(error = e.message) } }
        }
    }

    // ── Collaborators ─────────────────────────────────────────────────────────

    fun addCollaborator(email: String) {
        val machineId = _ui.value.activeMachine?.id ?: return
        viewModelScope.launch {
            machineRepo.addCollaborator(machineId, email).onSuccess { updated ->
                _ui.update { s ->
                    s.copy(
                        machines = s.machines.map { if (it.id == updated.id) updated else it },
                        activeMachine = updated,
                        toastMessage = "$email added",
                    )
                }
            }.onFailure { e -> _ui.update { it.copy(error = e.message) } }
        }
    }

    fun removeCollaborator(email: String) {
        val machineId = _ui.value.activeMachine?.id ?: return
        viewModelScope.launch {
            machineRepo.removeCollaborator(machineId, email).onSuccess { updated ->
                _ui.update { s ->
                    s.copy(
                        machines = s.machines.map { if (it.id == updated.id) updated else it },
                        activeMachine = updated,
                        toastMessage = "$email removed",
                    )
                }
            }.onFailure { e -> _ui.update { it.copy(error = e.message) } }
        }
    }

    // ── Logout ────────────────────────────────────────────────────────────────

    fun logout() {
        viewModelScope.launch {
            session.logout()
            api.logout()
        }
    }

    // ── Brew ──────────────────────────────────────────────────────────────────

    fun brew(cocktail: Cocktail, ingredients: List<Ingredient>) {
        val machineId = _ui.value.activeMachine?.id ?: return
        viewModelScope.launch {
            _ui.update { it.copy(isBrewing = true) }
            machineRepo.brew(machineId, BrewRequest(cocktail.idDrink, ingredients))
                .onSuccess {
                    loadHistory(machineId)
                    _ui.update {
                        it.copy(
                            isBrewing = false,
                            toastMessage = "Brewing ${cocktail.strDrink}!"
                        )
                    }
                }
                .onFailure { e ->
                    _ui.update { it.copy(isBrewing = false, error = e.message) }
                }
        }
    }

    // ── History ───────────────────────────────────────────────────────────────

    fun loadHistory(machineId: String) {
        viewModelScope.launch {
            machineRepo.getHistory(machineId).onSuccess { entries ->
                _ui.update { it.copy(history = entries) }
            }
        }
    }

    // ── Pumps ─────────────────────────────────────────────────────────────────

    fun loadPumps(machineId: String) {
        viewModelScope.launch {
            machineRepo.getPumps(machineId).onSuccess { pumps ->
                _ui.update { it.copy(pumps = pumps) }
            }
        }
    }

    fun createPump(pump: Pump) {
        val machineId = _ui.value.activeMachine?.id ?: return
        viewModelScope.launch {
            machineRepo.createPump(machineId, pump).onSuccess { created ->
                _ui.update { s ->
                    s.copy(
                        pumps = (s.pumps + created).sortedBy { it.pumpNumber },
                        toastMessage = "Pump ${created.pumpNumber} created",
                    )
                }
            }.onFailure { e -> _ui.update { it.copy(error = e.message) } }
        }
    }

    fun updatePump(pump: Pump) {
        val machineId = _ui.value.activeMachine?.id ?: return
        viewModelScope.launch {
            machineRepo.updatePump(machineId, pump).onSuccess { updated ->
                _ui.update { s ->
                    s.copy(pumps = s.pumps.map { if (it.pumpNumber == updated.pumpNumber) updated else it })
                }
            }.onFailure { e -> _ui.update { it.copy(error = e.message) } }
        }
    }

    fun deletePump(pumpNumber: Int) {
        val machineId = _ui.value.activeMachine?.id ?: return
        viewModelScope.launch {
            machineRepo.deletePump(machineId, pumpNumber).onSuccess {
                _ui.update { s ->
                    s.copy(
                        pumps = s.pumps.filter { it.pumpNumber != pumpNumber },
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
        viewModelScope.launch { machineRepo.triggerPump(machineId, pumpNumber) }
        testJob = viewModelScope.launch {
            for (i in 9 downTo 0) {
                delay(1_000)
                _ui.update { it.copy(testCountdown = i) }
            }
            _ui.update { it.copy(testingPump = null, testCountdown = 10) }
        }
    }

    // ── Dialog toggles ────────────────────────────────────────────────────────

    fun showCreateMachineDialog() = _ui.update { it.copy(showCreateMachine = true) }
    fun hideCreateMachineDialog() = _ui.update { it.copy(showCreateMachine = false) }
    fun showCollaboratorsDialog() = _ui.update { it.copy(showCollaborators = true) }
    fun hideCollaboratorsDialog() = _ui.update { it.copy(showCollaborators = false) }

    fun dismissToast() = _ui.update { it.copy(toastMessage = null) }
    fun dismissError() = _ui.update { it.copy(error = null) }
}
