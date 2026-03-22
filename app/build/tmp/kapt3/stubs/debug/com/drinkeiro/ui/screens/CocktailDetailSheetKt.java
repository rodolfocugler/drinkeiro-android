package com.drinkeiro.ui.screens;

import androidx.compose.foundation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import com.drinkeiro.data.model.Cocktail;
import com.drinkeiro.data.model.Ingredient;
import com.drinkeiro.ui.components.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000L\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a:\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001ap\u0010\n\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u001e\u0010\u000e\u001a\u001a\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a*\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0018\u0010\u0019\u001aO\u0010\u001a\u001a\u00020\u00012\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u0006\u0010\u001c\u001a\u00020\u00162\b\b\u0002\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u001d\u001a\u00020\u00162\u0011\u0010\u001e\u001a\r\u0012\u0004\u0012\u00020\u00010\b\u00a2\u0006\u0002\b\u001fH\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b \u0010!\u001a\b\u0010\"\u001a\u00020#H\u0003\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006$"}, d2 = {"BrewConfirmSheet", "", "cocktail", "Lcom/drinkeiro/data/model/Cocktail;", "finalIngs", "", "Lcom/drinkeiro/data/model/Ingredient;", "onConfirm", "Lkotlin/Function0;", "onDismiss", "CocktailDetailSheet", "isFav", "", "onToggleFav", "onBrew", "Lkotlin/Function2;", "onEdit", "onDelete", "MetaChip", "label", "", "bg", "Landroidx/compose/ui/graphics/Color;", "fg", "MetaChip-WkMS-hQ", "(Ljava/lang/String;JJ)V", "SmallIconBtn", "onClick", "tint", "border", "content", "Landroidx/compose/runtime/Composable;", "SmallIconBtn-f1JAnFk", "(Lkotlin/jvm/functions/Function0;JJJLkotlin/jvm/functions/Function0;)V", "fieldColors", "Landroidx/compose/material3/TextFieldColors;", "app_debug"})
public final class CocktailDetailSheetKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void CocktailDetailSheet(@org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.model.Cocktail cocktail, boolean isFav, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onToggleFav, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super com.drinkeiro.data.model.Cocktail, ? super java.util.List<com.drinkeiro.data.model.Ingredient>, kotlin.Unit> onBrew, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onEdit, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDelete, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void BrewConfirmSheet(com.drinkeiro.data.model.Cocktail cocktail, java.util.List<com.drinkeiro.data.model.Ingredient> finalIngs, kotlin.jvm.functions.Function0<kotlin.Unit> onConfirm, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final androidx.compose.material3.TextFieldColors fieldColors() {
        return null;
    }
}