package com.drinkeiro.viewmodel;

import androidx.lifecycle.ViewModel;
import com.drinkeiro.data.model.Cocktail;
import com.drinkeiro.data.repository.CocktailRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\r\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u0013\u001a\u00020\rJ\u0006\u0010\u0014\u001a\u00020\rJ\u0006\u0010\u0015\u001a\u00020\rJ\u0006\u0010\u0016\u001a\u00020\rJ\u0006\u0010\u0017\u001a\u00020\rJ\u0006\u0010\u0018\u001a\u00020\rJ\u000e\u0010\u0019\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u001a\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u001b\u001a\u00020\r2\u0006\u0010\u001c\u001a\u00020\u0012J\u000e\u0010\u001d\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u001e\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u001f"}, d2 = {"Lcom/drinkeiro/viewmodel/CocktailViewModel;", "Landroidx/lifecycle/ViewModel;", "repo", "Lcom/drinkeiro/data/repository/CocktailRepository;", "(Lcom/drinkeiro/data/repository/CocktailRepository;)V", "_ui", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/drinkeiro/viewmodel/CocktailUiState;", "ui", "Lkotlinx/coroutines/flow/StateFlow;", "getUi", "()Lkotlinx/coroutines/flow/StateFlow;", "createCocktail", "", "cocktail", "Lcom/drinkeiro/data/model/Cocktail;", "deleteCocktail", "idDrink", "", "dismissDeleteConfirm", "dismissError", "dismissRecipeEditor", "dismissToast", "loadAll", "requestCreateRecipe", "requestDelete", "requestEdit", "setCategory", "category", "toggleFavorite", "updateCocktail", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class CocktailViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.drinkeiro.data.repository.CocktailRepository repo = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.drinkeiro.viewmodel.CocktailUiState> _ui = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.drinkeiro.viewmodel.CocktailUiState> ui = null;
    
    @javax.inject.Inject()
    public CocktailViewModel(@org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.repository.CocktailRepository repo) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.drinkeiro.viewmodel.CocktailUiState> getUi() {
        return null;
    }
    
    public final void loadAll() {
    }
    
    public final void setCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String category) {
    }
    
    public final void toggleFavorite(@org.jetbrains.annotations.NotNull()
    java.lang.String idDrink) {
    }
    
    public final void createCocktail(@org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.model.Cocktail cocktail) {
    }
    
    public final void updateCocktail(@org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.model.Cocktail cocktail) {
    }
    
    public final void deleteCocktail(@org.jetbrains.annotations.NotNull()
    java.lang.String idDrink) {
    }
    
    public final void requestCreateRecipe() {
    }
    
    public final void requestEdit(@org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.model.Cocktail cocktail) {
    }
    
    public final void dismissRecipeEditor() {
    }
    
    public final void requestDelete(@org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.model.Cocktail cocktail) {
    }
    
    public final void dismissDeleteConfirm() {
    }
    
    public final void dismissToast() {
    }
    
    public final void dismissError() {
    }
}