package com.drinkeiro.viewmodel;

import androidx.lifecycle.ViewModel;
import com.drinkeiro.data.model.Cocktail;
import com.drinkeiro.data.repository.CocktailRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u001f\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001Bs\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0004\u0012\b\b\u0002\u0010\u000e\u001a\u00020\n\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\u0002\u0010\u0010J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0007H\u00c6\u0003J\t\u0010 \u001a\u00020\nH\u00c6\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000b\u0010#\u001a\u0004\u0018\u00010\u0004H\u00c6\u0003J\t\u0010$\u001a\u00020\nH\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0004H\u00c6\u0003Jw\u0010&\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u000e\u001a\u00020\n2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0004H\u00c6\u0001J\u0013\u0010\'\u001a\u00020\n2\b\u0010(\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010)\u001a\u00020*H\u00d6\u0001J\t\u0010+\u001a\u00020\u0007H\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u000e\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0019R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0019R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0014R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0016R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016\u00a8\u0006,"}, d2 = {"Lcom/drinkeiro/viewmodel/CocktailUiState;", "", "cocktails", "", "Lcom/drinkeiro/data/model/Cocktail;", "favorites", "", "", "selectedCategory", "isLoading", "", "error", "toastMessage", "recipeEditorCocktail", "isCreatingRecipe", "deleteConfirmCocktail", "(Ljava/util/List;Ljava/util/Set;Ljava/lang/String;ZLjava/lang/String;Ljava/lang/String;Lcom/drinkeiro/data/model/Cocktail;ZLcom/drinkeiro/data/model/Cocktail;)V", "getCocktails", "()Ljava/util/List;", "getDeleteConfirmCocktail", "()Lcom/drinkeiro/data/model/Cocktail;", "getError", "()Ljava/lang/String;", "getFavorites", "()Ljava/util/Set;", "()Z", "getRecipeEditorCocktail", "getSelectedCategory", "getToastMessage", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class CocktailUiState {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.drinkeiro.data.model.Cocktail> cocktails = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<java.lang.String> favorites = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String selectedCategory = null;
    private final boolean isLoading = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String toastMessage = null;
    @org.jetbrains.annotations.Nullable()
    private final com.drinkeiro.data.model.Cocktail recipeEditorCocktail = null;
    private final boolean isCreatingRecipe = false;
    @org.jetbrains.annotations.Nullable()
    private final com.drinkeiro.data.model.Cocktail deleteConfirmCocktail = null;
    
    public CocktailUiState(@org.jetbrains.annotations.NotNull()
    java.util.List<com.drinkeiro.data.model.Cocktail> cocktails, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> favorites, @org.jetbrains.annotations.NotNull()
    java.lang.String selectedCategory, boolean isLoading, @org.jetbrains.annotations.Nullable()
    java.lang.String error, @org.jetbrains.annotations.Nullable()
    java.lang.String toastMessage, @org.jetbrains.annotations.Nullable()
    com.drinkeiro.data.model.Cocktail recipeEditorCocktail, boolean isCreatingRecipe, @org.jetbrains.annotations.Nullable()
    com.drinkeiro.data.model.Cocktail deleteConfirmCocktail) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.drinkeiro.data.model.Cocktail> getCocktails() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.String> getFavorites() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSelectedCategory() {
        return null;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getToastMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.drinkeiro.data.model.Cocktail getRecipeEditorCocktail() {
        return null;
    }
    
    public final boolean isCreatingRecipe() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.drinkeiro.data.model.Cocktail getDeleteConfirmCocktail() {
        return null;
    }
    
    public CocktailUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.drinkeiro.data.model.Cocktail> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.String> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    public final boolean component4() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.drinkeiro.data.model.Cocktail component7() {
        return null;
    }
    
    public final boolean component8() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.drinkeiro.data.model.Cocktail component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.drinkeiro.viewmodel.CocktailUiState copy(@org.jetbrains.annotations.NotNull()
    java.util.List<com.drinkeiro.data.model.Cocktail> cocktails, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> favorites, @org.jetbrains.annotations.NotNull()
    java.lang.String selectedCategory, boolean isLoading, @org.jetbrains.annotations.Nullable()
    java.lang.String error, @org.jetbrains.annotations.Nullable()
    java.lang.String toastMessage, @org.jetbrains.annotations.Nullable()
    com.drinkeiro.data.model.Cocktail recipeEditorCocktail, boolean isCreatingRecipe, @org.jetbrains.annotations.Nullable()
    com.drinkeiro.data.model.Cocktail deleteConfirmCocktail) {
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