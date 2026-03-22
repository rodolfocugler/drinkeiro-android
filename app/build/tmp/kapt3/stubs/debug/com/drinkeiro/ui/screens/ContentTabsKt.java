package com.drinkeiro.ui.screens;

import androidx.compose.foundation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import com.drinkeiro.data.model.Cocktail;
import com.drinkeiro.ui.components.*;
import com.drinkeiro.viewmodel.CocktailViewModel;
import com.drinkeiro.viewmodel.MachineViewModel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000 \n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007\u001a\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0002H\u0003\u001a\u0018\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007\u001a\u0018\u0010\f\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u001a\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0006H\u0007\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"CATEGORIES", "", "", "CocktailsTab", "", "cocktailVm", "Lcom/drinkeiro/viewmodel/CocktailViewModel;", "machineVm", "Lcom/drinkeiro/viewmodel/MachineViewModel;", "EmptyState", "text", "FavoritesTab", "HistoryTab", "ObserveRecipeDialogs", "vm", "app_debug"})
public final class ContentTabsKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> CATEGORIES = null;
    
    @androidx.compose.runtime.Composable()
    public static final void FavoritesTab(@org.jetbrains.annotations.NotNull()
    com.drinkeiro.viewmodel.CocktailViewModel cocktailVm, @org.jetbrains.annotations.NotNull()
    com.drinkeiro.viewmodel.MachineViewModel machineVm) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CocktailsTab(@org.jetbrains.annotations.NotNull()
    com.drinkeiro.viewmodel.CocktailViewModel cocktailVm, @org.jetbrains.annotations.NotNull()
    com.drinkeiro.viewmodel.MachineViewModel machineVm) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void HistoryTab(@org.jetbrains.annotations.NotNull()
    com.drinkeiro.viewmodel.MachineViewModel machineVm, @org.jetbrains.annotations.NotNull()
    com.drinkeiro.viewmodel.CocktailViewModel cocktailVm) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmptyState(java.lang.String text) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ObserveRecipeDialogs(@org.jetbrains.annotations.NotNull()
    com.drinkeiro.viewmodel.CocktailViewModel vm) {
    }
}