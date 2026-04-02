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
    private val localCocktails  = mutableListOf<Cocktail>()
    private val localFavorites  = mutableListOf<Cocktail>()

    // ── Cocktails ─────────────────────────────────────────────────────────────

    suspend fun getCocktails(
        category: String? = null,
        search:   String? = null,
        page:     Int     = 0,
        pageSize: Int     = 20,
    ): Result<List<Cocktail>> = try {
        val r = api.getCocktails(
            category = category,
            search   = search,
            page     = page,
            pageSize = pageSize,
        )
        if (r.isSuccessful) {
            val list = r.body()!!.content
            if (page == 0) { localCocktails.clear(); localCocktails.addAll(list) }
            else localCocktails.addAll(list)
            Result.success(list)
        } else {
            Log.w(TAG, "getCocktails HTTP ${r.code()} — local fallback")
            Result.success(localPage(category, search, page, pageSize))
        }
    } catch (e: Exception) {
        Log.w(TAG, "getCocktails offline: ${e.message}")
        Result.success(localPage(category, search, page, pageSize))
    }

    private fun localPage(category: String?, search: String?, page: Int, pageSize: Int): List<Cocktail> {
        var list = if (category == null) localCocktails.toList()
                   else localCocktails.filter { it.strCategory == category }
        if (!search.isNullOrBlank()) {
            val q = search.lowercase()
            list = list.filter {
                it.strDrink.lowercase().contains(q) ||
                it.strIngredient.any { i -> i.strIngredient.lowercase().contains(q) }
            }
        }
        val from = page * pageSize
        return if (from >= list.size) emptyList()
               else list.subList(from, minOf(from + pageSize, list.size))
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
        val favIdx = localFavorites.indexOfFirst { it.idDrink == cocktail.idDrink }
        if (favIdx >= 0) localFavorites[favIdx] = cocktail
        return try {
            val r = api.updateCocktail(cocktail.idDrink, cocktail)
            if (r.isSuccessful) Result.success(r.body()!!) else Result.success(cocktail)
        } catch (e: Exception) { Result.success(cocktail) }
    }

    suspend fun deleteCocktail(id: String): Result<Unit> {
        localCocktails.removeAll { it.idDrink == id }
        localFavorites.removeAll { it.idDrink == id }
        return try { api.deleteCocktail(id); Result.success(Unit) }
        catch (e: Exception) { Result.success(Unit) }
    }

    // ── Favorites — full objects from backend, with pagination ────────────────

    suspend fun getFavorites(
        page:     Int = 0,
        search:   String? = null,
        pageSize: Int = 20,
    ): Result<List<Cocktail>> = try {
        val r = api.getFavorites(page = page, search = search, pageSize = pageSize)
        if (r.isSuccessful) {
            val list = r.body()!!.content
            if (page == 0) { localFavorites.clear(); localFavorites.addAll(list) }
            else localFavorites.addAll(list)
            Result.success(list)
        } else {
            Log.w(TAG, "getFavorites HTTP ${r.code()} — local fallback")
            val from = page * pageSize
            val local = if (from >= localFavorites.size) emptyList()
                        else localFavorites.subList(from, minOf(from + pageSize, localFavorites.size))
            Result.success(local)
        }
    } catch (e: Exception) {
        Log.w(TAG, "getFavorites offline: ${e.message}")
        val from = page * pageSize
        val local = if (from >= localFavorites.size) emptyList()
                    else localFavorites.subList(from, minOf(from + pageSize, localFavorites.size))
        Result.success(local)
    }

    suspend fun addFavorite(id: String): Result<Unit> = try {
        api.addFavorite(id); Result.success(Unit)
    } catch (e: Exception) { Result.success(Unit) }

    suspend fun removeFavorite(id: String): Result<Unit> {
        localFavorites.removeAll { it.id == id }
        return try { api.removeFavorite(id); Result.success(Unit) }
        catch (e: Exception) { Result.success(Unit) }
    }
}
