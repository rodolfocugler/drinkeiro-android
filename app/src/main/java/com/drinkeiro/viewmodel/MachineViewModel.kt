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

private const val HISTORY_PAGE_SIZE = 20

data class MachineUiState(
    val machines: List<Machine> = emptyList(),
    val activeMachine: Machine? = null,
    val pumps: List<Pump> = emptyList(),

    // History — paginated
    val history: List<HistoryEntry> = emptyList(),
    val isHistoryLoading: Boolean = false,
    val isHistoryLoadMore: Boolean = false,
    val isHistoryRefresh: Boolean = false,
    val historyPage: Int = 0,
    val canHistoryMore: Boolean = true,

    // General
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isBrewing: Boolean = false,
    val toastMessage: String? = null,
    val error: String? = null,
    val testingPump: Int? = null,
    val testCountdown: Int = 10,

    // Dialog flags
    val showCollaborators: Boolean = false,
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

    val activeMachineId: StateFlow<String?> = session.activeMachineIdState

    private var testJob: Job? = null

    init {
        viewModelScope.launch { session.init() }
        loadMachines()
    }

    // ── Machines ──────────────────────────────────────────────────────────────

    fun loadMachines(refresh: Boolean = false) {
        viewModelScope.launch {
            if (refresh) _ui.update { it.copy(isRefreshing = true) }
            else _ui.update { it.copy(isLoading = true) }

            machineRepo.getMachines().onSuccess { list ->
                val savedId = session.activeMachineIdState.value
                    ?: session.activeMachineId.firstOrNull()
                val active = list.firstOrNull { it.id == savedId } ?: list.firstOrNull()
                _ui.update {
                    it.copy(
                        machines = list,
                        activeMachine = active,
                        isLoading = false,
                        isRefreshing = false,
                    )
                }
                if (active != null) {
                    session.setActiveMachineInMemory(active.id)
                    loadPumps(active.id)
                    loadHistory(refresh = refresh)
                }
            }.onFailure { e ->
                _ui.update { it.copy(isLoading = false, isRefreshing = false, error = e.message) }
            }
        }
    }

    fun refresh() = loadMachines(refresh = true)

    fun selectMachine(machine: Machine) {
        _ui.update {
            it.copy(
                activeMachine = machine,
                history = emptyList(),
                historyPage = 0,
                canHistoryMore = true
            )
        }
        viewModelScope.launch {
            session.setActiveMachine(machine.id)
            loadPumps(machine.id)
            loadHistory(refresh = false)
        }
    }

    fun createMachine(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            machineRepo.createMachine(
                Machine(
                    id = "",
                    name = name.trim(),
                    status = "offline",
                    collaborators = emptyList()
                )
            )
                .onSuccess { created ->
                    _ui.update { s ->
                        s.copy(
                            machines = s.machines + created,
                            showCreateMachine = false,
                            toastMessage = "\"${created.name}\" created"
                        )
                    }
                }.onFailure { e -> _ui.update { it.copy(error = e.message) } }
        }
    }

    fun updateMachine(machine: Machine) {
        viewModelScope.launch {
            machineRepo.updateMachine(machine).onSuccess { updated ->
                _ui.update { s ->
                    s.copy(
                        machines = s.machines.map { if (it.id == updated.id) updated else it },
                        activeMachine = if (s.activeMachine?.id == updated.id) updated else s.activeMachine,
                        toastMessage = "\"${updated.name}\" updated",
                    )
                }
            }.onFailure { e -> _ui.update { it.copy(error = e.message) } }
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
                    s.copy(
                        machines = remaining,
                        activeMachine = newActive,
                        toastMessage = "Machine deleted",
                    )
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
            try {
                api.logout()
            } catch (_: Exception) {
            }
        }
    }

    // ── Brew ──────────────────────────────────────────────────────────────────

    fun brew(cocktail: CocktailDto, ingredients: List<Ingredient>) {
        val machineId = _ui.value.activeMachine?.id ?: return
        viewModelScope.launch {
            _ui.update { it.copy(isBrewing = true) }
            machineRepo.brew(machineId, BrewRequest(cocktail.id, ingredients))
                .onSuccess {
                    // Reload history from page 0 after a brew
                    loadHistory(refresh = false)
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

    fun loadHistory(refresh: Boolean = false) {
        val machineId = _ui.value.activeMachine?.id ?: return
        if (refresh) {
            _ui.update { it.copy(isHistoryRefresh = true, historyPage = 0, canHistoryMore = true) }
        } else {
            if (_ui.value.isHistoryLoading) return
            _ui.update { it.copy(isHistoryLoading = true) }
        }
        viewModelScope.launch {
            machineRepo.getHistory(machineId, page = 0, pageSize = HISTORY_PAGE_SIZE)
                .onSuccess { entries ->
                    _ui.update { s ->
                        s.copy(
                            history = entries,
                            historyPage = 0,
                            canHistoryMore = entries.size >= HISTORY_PAGE_SIZE,
                            isHistoryLoading = false,
                            isHistoryRefresh = false,
                        )
                    }
                }
                .onFailure {
                    _ui.update { it.copy(isHistoryLoading = false, isHistoryRefresh = false) }
                }
        }
    }

    fun loadMoreHistory() {
        val s = _ui.value
        if (!s.canHistoryMore || s.isHistoryLoadMore || s.isHistoryLoading) return
        val machineId = s.activeMachine?.id ?: return
        val nextPage = s.historyPage + 1
        viewModelScope.launch {
            _ui.update { it.copy(isHistoryLoadMore = true) }
            machineRepo.getHistory(machineId, page = nextPage, pageSize = HISTORY_PAGE_SIZE)
                .onSuccess { newItems ->
                    _ui.update { st ->
                        st.copy(
                            history = st.history + newItems,
                            historyPage = nextPage,
                            canHistoryMore = newItems.size >= HISTORY_PAGE_SIZE,
                            isHistoryLoadMore = false,
                        )
                    }
                }
                .onFailure { _ui.update { it.copy(isHistoryLoadMore = false) } }
        }
    }

    fun refreshHistory() = loadHistory(refresh = true)

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
                        pumps = (s.pumps + created).sortedBy { it.port },
                        toastMessage = "Pump ${created.port} created",
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
                    s.copy(pumps = s.pumps.map { if (it.port == updated.port) updated else it })
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
                        pumps = s.pumps.filter { it.port != pumpNumber },
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
