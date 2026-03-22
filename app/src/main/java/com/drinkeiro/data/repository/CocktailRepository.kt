package com.drinkeiro.data.repository

import com.drinkeiro.data.api.DrinkeiroApi
import com.drinkeiro.data.model.Cocktail
import com.drinkeiro.data.model.Ingredient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CocktailRepository @Inject constructor(
    private val api: DrinkeiroApi,
) {
    suspend fun getCocktails(category: String? = null): Result<List<Cocktail>> = runCatching {
        val r = api.getCocktails(category)
        if (r.isSuccessful) r.body()!!.data
        else error("HTTP ${r.code()}: ${r.message()}")
    }

    suspend fun getCocktail(id: String): Result<Cocktail> = runCatching {
        val r = api.getCocktail(id)
        if (r.isSuccessful) r.body()!!
        else error("HTTP ${r.code()}")
    }

    suspend fun createCocktail(cocktail: Cocktail): Result<Cocktail> = runCatching {
        val r = api.createCocktail(cocktail)
        if (r.isSuccessful) r.body()!!
        else error("HTTP ${r.code()}")
    }

    suspend fun updateCocktail(cocktail: Cocktail): Result<Cocktail> = runCatching {
        val r = api.updateCocktail(cocktail.idDrink, cocktail)
        if (r.isSuccessful) r.body()!!
        else error("HTTP ${r.code()}")
    }

    suspend fun deleteCocktail(id: String): Result<Unit> = runCatching {
        val r = api.deleteCocktail(id)
        if (!r.isSuccessful) error("HTTP ${r.code()}")
    }

    suspend fun getFavorites(): Result<List<Cocktail>> = runCatching {
        val r = api.getFavorites()
        if (r.isSuccessful) r.body()!!.data
        else error("HTTP ${r.code()}")
    }

    suspend fun addFavorite(idDrink: String): Result<Unit> = runCatching {
        val r = api.addFavorite(idDrink)
        if (!r.isSuccessful) error("HTTP ${r.code()}")
    }

    suspend fun removeFavorite(idDrink: String): Result<Unit> = runCatching {
        val r = api.removeFavorite(idDrink)
        if (!r.isSuccessful) error("HTTP ${r.code()}")
    }
}
