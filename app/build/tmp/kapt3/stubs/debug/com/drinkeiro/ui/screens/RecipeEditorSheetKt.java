package com.drinkeiro.ui.screens;

import androidx.compose.foundation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.drinkeiro.data.model.Cocktail;
import com.drinkeiro.data.model.Ingredient;
import com.drinkeiro.ui.components.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00008\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a2\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\nH\u0003\u001a:\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00060\n2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u0012H\u0007\u001a\b\u0010\u0013\u001a\u00020\u0014H\u0003\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"ALCOHOLIC", "", "", "CATEGORIES", "CC_OPTIONS", "DropdownField", "", "value", "options", "onSelect", "Lkotlin/Function1;", "RecipeEditorSheet", "cocktail", "Lcom/drinkeiro/data/model/Cocktail;", "isNew", "", "onSave", "onDismiss", "Lkotlin/Function0;", "editorFieldColors", "Landroidx/compose/material3/TextFieldColors;", "app_debug"})
@kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
public final class RecipeEditorSheetKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> CATEGORIES = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> ALCOHOLIC = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> CC_OPTIONS = null;
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void RecipeEditorSheet(@org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.model.Cocktail cocktail, boolean isNew, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.drinkeiro.data.model.Cocktail, kotlin.Unit> onSave, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DropdownField(java.lang.String value, java.util.List<java.lang.String> options, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSelect) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final androidx.compose.material3.TextFieldColors editorFieldColors() {
        return null;
    }
}