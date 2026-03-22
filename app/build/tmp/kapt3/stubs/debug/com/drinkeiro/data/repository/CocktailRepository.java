package com.drinkeiro.data.repository;

import com.drinkeiro.data.api.DrinkeiroApi;
import com.drinkeiro.data.model.Cocktail;
import com.drinkeiro.data.model.Ingredient;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0002\b\n\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J$\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ$\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00062\u0006\u0010\u000e\u001a\u00020\rH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000f\u0010\u0010J$\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\u0012\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0013\u0010\u000bJ$\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\r0\u00062\u0006\u0010\u0012\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0015\u0010\u000bJ.\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00170\u00062\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0019\u0010\u000bJ\"\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00170\u0006H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001b\u0010\u001cJ$\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001e\u0010\u000bJ$\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\r0\u00062\u0006\u0010\u000e\u001a\u00020\rH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b \u0010\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006!"}, d2 = {"Lcom/drinkeiro/data/repository/CocktailRepository;", "", "api", "Lcom/drinkeiro/data/api/DrinkeiroApi;", "(Lcom/drinkeiro/data/api/DrinkeiroApi;)V", "addFavorite", "Lkotlin/Result;", "", "idDrink", "", "addFavorite-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createCocktail", "Lcom/drinkeiro/data/model/Cocktail;", "cocktail", "createCocktail-gIAlu-s", "(Lcom/drinkeiro/data/model/Cocktail;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteCocktail", "id", "deleteCocktail-gIAlu-s", "getCocktail", "getCocktail-gIAlu-s", "getCocktails", "", "category", "getCocktails-gIAlu-s", "getFavorites", "getFavorites-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "removeFavorite", "removeFavorite-gIAlu-s", "updateCocktail", "updateCocktail-gIAlu-s", "app_debug"})
public final class CocktailRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.drinkeiro.data.api.DrinkeiroApi api = null;
    
    @javax.inject.Inject()
    public CocktailRepository(@org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.api.DrinkeiroApi api) {
        super();
    }
}