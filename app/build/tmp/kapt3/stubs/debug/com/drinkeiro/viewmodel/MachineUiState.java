package com.drinkeiro.viewmodel;

import androidx.lifecycle.ViewModel;
import com.drinkeiro.data.model.*;
import com.drinkeiro.data.repository.MachineRepository;
import com.drinkeiro.data.repository.SessionRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\"\b\u0087\b\u0018\u00002\u00020\u0001B\u0083\u0001\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0002\u0010\u0013J\u000f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u0011H\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0004H\u00c6\u0003J\u000f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003H\u00c6\u0003J\u000f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\t0\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u000bH\u00c6\u0003J\t\u0010)\u001a\u00020\u000bH\u00c6\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003J\u000b\u0010+\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003J\u0010\u0010,\u001a\u0004\u0018\u00010\u0011H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u008c\u0001\u0010-\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u00032\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0011H\u00c6\u0001\u00a2\u0006\u0002\u0010.J\u0013\u0010/\u001a\u00020\u000b2\b\u00100\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00101\u001a\u00020\u0011H\u00d6\u0001J\t\u00102\u001a\u00020\u000eH\u00d6\u0001R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\f\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u001aR\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u001aR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0019R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0019R\u0011\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0015\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b\u001f\u0010 R\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0017\u00a8\u00063"}, d2 = {"Lcom/drinkeiro/viewmodel/MachineUiState;", "", "machines", "", "Lcom/drinkeiro/data/model/Machine;", "activeMachine", "pumps", "Lcom/drinkeiro/data/model/Pump;", "history", "Lcom/drinkeiro/data/model/HistoryEntry;", "isLoading", "", "isBrewing", "toastMessage", "", "error", "testingPump", "", "testCountdown", "(Ljava/util/List;Lcom/drinkeiro/data/model/Machine;Ljava/util/List;Ljava/util/List;ZZLjava/lang/String;Ljava/lang/String;Ljava/lang/Integer;I)V", "getActiveMachine", "()Lcom/drinkeiro/data/model/Machine;", "getError", "()Ljava/lang/String;", "getHistory", "()Ljava/util/List;", "()Z", "getMachines", "getPumps", "getTestCountdown", "()I", "getTestingPump", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getToastMessage", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/util/List;Lcom/drinkeiro/data/model/Machine;Ljava/util/List;Ljava/util/List;ZZLjava/lang/String;Ljava/lang/String;Ljava/lang/Integer;I)Lcom/drinkeiro/viewmodel/MachineUiState;", "equals", "other", "hashCode", "toString", "app_debug"})
public final class MachineUiState {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.drinkeiro.data.model.Machine> machines = null;
    @org.jetbrains.annotations.Nullable()
    private final com.drinkeiro.data.model.Machine activeMachine = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.drinkeiro.data.model.Pump> pumps = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.drinkeiro.data.model.HistoryEntry> history = null;
    private final boolean isLoading = false;
    private final boolean isBrewing = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String toastMessage = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer testingPump = null;
    private final int testCountdown = 0;
    
    public MachineUiState(@org.jetbrains.annotations.NotNull()
    java.util.List<com.drinkeiro.data.model.Machine> machines, @org.jetbrains.annotations.Nullable()
    com.drinkeiro.data.model.Machine activeMachine, @org.jetbrains.annotations.NotNull()
    java.util.List<com.drinkeiro.data.model.Pump> pumps, @org.jetbrains.annotations.NotNull()
    java.util.List<com.drinkeiro.data.model.HistoryEntry> history, boolean isLoading, boolean isBrewing, @org.jetbrains.annotations.Nullable()
    java.lang.String toastMessage, @org.jetbrains.annotations.Nullable()
    java.lang.String error, @org.jetbrains.annotations.Nullable()
    java.lang.Integer testingPump, int testCountdown) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.drinkeiro.data.model.Machine> getMachines() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.drinkeiro.data.model.Machine getActiveMachine() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.drinkeiro.data.model.Pump> getPumps() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.drinkeiro.data.model.HistoryEntry> getHistory() {
        return null;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    public final boolean isBrewing() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getToastMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getTestingPump() {
        return null;
    }
    
    public final int getTestCountdown() {
        return 0;
    }
    
    public MachineUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.drinkeiro.data.model.Machine> component1() {
        return null;
    }
    
    public final int component10() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.drinkeiro.data.model.Machine component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.drinkeiro.data.model.Pump> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.drinkeiro.data.model.HistoryEntry> component4() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final boolean component6() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.drinkeiro.viewmodel.MachineUiState copy(@org.jetbrains.annotations.NotNull()
    java.util.List<com.drinkeiro.data.model.Machine> machines, @org.jetbrains.annotations.Nullable()
    com.drinkeiro.data.model.Machine activeMachine, @org.jetbrains.annotations.NotNull()
    java.util.List<com.drinkeiro.data.model.Pump> pumps, @org.jetbrains.annotations.NotNull()
    java.util.List<com.drinkeiro.data.model.HistoryEntry> history, boolean isLoading, boolean isBrewing, @org.jetbrains.annotations.Nullable()
    java.lang.String toastMessage, @org.jetbrains.annotations.Nullable()
    java.lang.String error, @org.jetbrains.annotations.Nullable()
    java.lang.Integer testingPump, int testCountdown) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}