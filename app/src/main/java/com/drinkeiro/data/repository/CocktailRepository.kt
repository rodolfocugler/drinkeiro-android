package com.drinkeiro.data.repository

import android.util.Log
import com.drinkeiro.data.api.DrinkeiroApi
import com.drinkeiro.data.model.Cocktail
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "CocktailRepo"

@Singleton
class CocktailRepository @Inject constructor(
    private val api: DrinkeiroApi,
) {
    private val localCocktails = mutableListOf<Cocktail>()
    private val localFavorites = mutableListOf<String>()

    suspend fun getCocktails(
        category: String? = null,
        page:     Int     = 0,
        pageSize: Int     = 20,
    ): Result<List<Cocktail>> {
        return try {
            val r = api.getCocktails(category = category, page = page, pageSize = pageSize)
            if (r.isSuccessful) {
                val list = r.body()!!.content
                if (page == 0) {
                    localCocktails.clear()
                    localCocktails.addAll(list)
                } else {
                    localCocktails.addAll(list)
                }
                Result.success(list)
            } else {
                Log.w(TAG, "HTTP ${r.code()} — local fallback (page=$page)")
                Result.success(localPage(category, page, pageSize))
            }
        } catch (e: Exception) {
            Log.w(TAG, "Offline — local fallback: ${e.message}")
            Result.success(localPage(category, page, pageSize))
        }
    }

    private fun localPage(category: String?, page: Int, pageSize: Int): List<Cocktail> {
        val filtered = if (category == null) localCocktails
                       else localCocktails.filter { it.strCategory == category }
        val from = page * pageSize
        return if (from >= filtered.size) emptyList()
               else filtered.subList(from, minOf(from + pageSize, filtered.size))
    }

    suspend fun getCocktail(id: String): Result<Cocktail> = try {
        val r = api.getCocktail(id)
        if (r.isSuccessful) Result.success(r.body()!!)
        else localCocktails.find { it.idDrink == id }
            ?.let { Result.success(it) } ?: Result.failure(Exception("Not found"))
    } catch (e: Exception) {
        localCocktails.find { it.idDrink == id }
            ?.let { Result.success(it) } ?: Result.failure(e)
    }

    suspend fun createCocktail(cocktail: Cocktail): Result<Cocktail> {
        localCocktails.add(0, cocktail)
        return try {
            val r = api.createCocktail(cocktail)
            if (r.isSuccessful) Result.success(r.body()!!) else Result.success(cocktail)
        } catch (e: Exception) { Result.success(cocktail) }
    }

    suspend fun updateCocktail(cocktail: Cocktail): Result<Cocktail> {
        val idx = localCocktails.indexOfFirst { it.idDrink == cocktail.idDrink }
        if (idx >= 0) localCocktails[idx] = cocktail
        return try {
            val r = api.updateCocktail(cocktail.idDrink, cocktail)
            if (r.isSuccessful) Result.success(r.body()!!) else Result.success(cocktail)
        } catch (e: Exception) { Result.success(cocktail) }
    }

    suspend fun deleteCocktail(id: String): Result<Unit> {
        localCocktails.removeAll { it.idDrink == id }
        return try {
            val r = api.deleteCocktail(id)
            Result.success(Unit)
        } catch (e: Exception) { Result.success(Unit) }
    }

    suspend fun getFavorites(): Result<List<Cocktail>> = try {
        val r = api.getFavorites()
        if (r.isSuccessful) {
            val list = r.body()!!
            localFavorites.clear()
            localFavorites.addAll(list.map { it.idDrink })
            Result.success(list)
        } else {
            Result.success(localCocktails.filter { localFavorites.contains(it.idDrink) })
        }
    } catch (e: Exception) {
        Result.success(localCocktails.filter { localFavorites.contains(it.idDrink) })
    }

    suspend fun addFavorite(idDrink: String): Result<Unit> {
        localFavorites.add(idDrink)
        return try { api.addFavorite(idDrink); Result.success(Unit) }
        catch (e: Exception) { Result.success(Unit) }
    }

    suspend fun removeFavorite(idDrink: String): Result<Unit> {
        localFavorites.remove(idDrink)
        return try { api.removeFavorite(idDrink); Result.success(Unit) }
        catch (e: Exception) { Result.success(Unit) }
    }
}
