package com.drinkeiro.ui.screens;

import androidx.compose.animation.core.*;
import androidx.compose.foundation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import com.drinkeiro.data.model.Pump;
import com.drinkeiro.ui.components.*;
import com.drinkeiro.viewmodel.MachineViewModel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0007\u001a\u001e\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0007\u001aH\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0007\u001a*\u0010\u0011\u001a\u00020\u00012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\r2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\u0010H\u0003\u001aR\u0010\u0014\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u000b2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0003\u00a8\u0006\u001b"}, d2 = {"MachineSettingsTab", "", "vm", "Lcom/drinkeiro/viewmodel/MachineViewModel;", "MachineSwitcherSheet", "onDismiss", "Lkotlin/Function0;", "PumpEditorSheet", "pump", "Lcom/drinkeiro/data/model/Pump;", "isNew", "", "usedNumbers", "", "", "onSave", "Lkotlin/Function1;", "PumpGrid", "pumps", "onPumpTap", "PumpRow", "isTesting", "countdown", "anyTesting", "onTest", "onEdit", "onDelete", "app_debug"})
public final class MachineSettingsTabKt {
    
    @androidx.compose.runtime.Composable()
    public static final void MachineSettingsTab(@org.jetbrains.annotations.NotNull()
    com.drinkeiro.viewmodel.MachineViewModel vm) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PumpGrid(java.util.List<com.drinkeiro.data.model.Pump> pumps, kotlin.jvm.functions.Function1<? super com.drinkeiro.data.model.Pump, kotlin.Unit> onPumpTap) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PumpRow(com.drinkeiro.data.model.Pump pump, boolean isTesting, int countdown, boolean anyTesting, kotlin.jvm.functions.Function0<kotlin.Unit> onTest, kotlin.jvm.functions.Function0<kotlin.Unit> onEdit, kotlin.jvm.functions.Function0<kotlin.Unit> onDelete) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void MachineSwitcherSheet(@org.jetbrains.annotations.NotNull()
    com.drinkeiro.viewmodel.MachineViewModel vm, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void PumpEditorSheet(@org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.model.Pump pump, boolean isNew, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Integer> usedNumbers, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.drinkeiro.data.model.Pump, kotlin.Unit> onSave, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
}