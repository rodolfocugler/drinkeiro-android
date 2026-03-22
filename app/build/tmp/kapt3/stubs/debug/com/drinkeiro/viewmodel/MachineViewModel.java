package com.drinkeiro.viewmodel;

import androidx.lifecycle.ViewModel;
import com.drinkeiro.data.model.*;
import com.drinkeiro.data.repository.MachineRepository;
import com.drinkeiro.data.repository.SessionRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015J\u000e\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u001cJ\u0006\u0010\u001d\u001a\u00020\u0011J\u0006\u0010\u001e\u001a\u00020\u0011J\u000e\u0010\u001f\u001a\u00020\u00112\u0006\u0010 \u001a\u00020!J\u0006\u0010\"\u001a\u00020\u0011J\u000e\u0010#\u001a\u00020\u00112\u0006\u0010 \u001a\u00020!J\u000e\u0010$\u001a\u00020\u00112\u0006\u0010%\u001a\u00020&J\u000e\u0010\'\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010(\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u0019R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006)"}, d2 = {"Lcom/drinkeiro/viewmodel/MachineViewModel;", "Landroidx/lifecycle/ViewModel;", "repo", "Lcom/drinkeiro/data/repository/MachineRepository;", "session", "Lcom/drinkeiro/data/repository/SessionRepository;", "(Lcom/drinkeiro/data/repository/MachineRepository;Lcom/drinkeiro/data/repository/SessionRepository;)V", "_ui", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/drinkeiro/viewmodel/MachineUiState;", "testJob", "Lkotlinx/coroutines/Job;", "ui", "Lkotlinx/coroutines/flow/StateFlow;", "getUi", "()Lkotlinx/coroutines/flow/StateFlow;", "brew", "", "cocktail", "Lcom/drinkeiro/data/model/Cocktail;", "ingredients", "", "Lcom/drinkeiro/data/model/Ingredient;", "createPump", "pump", "Lcom/drinkeiro/data/model/Pump;", "deletePump", "pumpNumber", "", "dismissError", "dismissToast", "loadHistory", "machineId", "", "loadMachines", "loadPumps", "selectMachine", "machine", "Lcom/drinkeiro/data/model/Machine;", "triggerPump", "updatePump", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class MachineViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.drinkeiro.data.repository.MachineRepository repo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.drinkeiro.data.repository.SessionRepository session = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.drinkeiro.viewmodel.MachineUiState> _ui = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.drinkeiro.viewmodel.MachineUiState> ui = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job testJob;
    
    @javax.inject.Inject()
    public MachineViewModel(@org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.repository.MachineRepository repo, @org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.repository.SessionRepository session) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.drinkeiro.viewmodel.MachineUiState> getUi() {
        return null;
    }
    
    public final void loadMachines() {
    }
    
    public final void selectMachine(@org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.model.Machine machine) {
    }
    
    public final void brew(@org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.model.Cocktail cocktail, @org.jetbrains.annotations.NotNull()
    java.util.List<com.drinkeiro.data.model.Ingredient> ingredients) {
    }
    
    public final void loadHistory(@org.jetbrains.annotations.NotNull()
    java.lang.String machineId) {
    }
    
    public final void loadPumps(@org.jetbrains.annotations.NotNull()
    java.lang.String machineId) {
    }
    
    public final void createPump(@org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.model.Pump pump) {
    }
    
    public final void updatePump(@org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.model.Pump pump) {
    }
    
    public final void deletePump(int pumpNumber) {
    }
    
    public final void triggerPump(int pumpNumber) {
    }
    
    public final void dismissToast() {
    }
    
    public final void dismissError() {
    }
}