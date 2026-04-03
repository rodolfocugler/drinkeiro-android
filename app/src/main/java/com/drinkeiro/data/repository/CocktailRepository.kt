package com.drinkeiro.data.repository

import android.util.Log
import com.drinkeiro.data.api.DrinkeiroApi
import com.drinkeiro.data.model.CocktailDto
import com.drinkeiro.data.model.CocktailEntity
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "CocktailRepo"

@Singleton
class CocktailRepository @Inject constructor(
    private val api: DrinkeiroApi,
) {
    // Local cache used as fallback when offline
    private val localCocktails  = mutableListOf<CocktailDto>()
    private val localFavorites  = mutableListOf<CocktailDto>()   // full objects now

    suspend fun getCategories(): Result<List<String>> = try {
        val r = api.getCategories()
        if (r.isSuccessful) Result.success(r.body()!!.map { it.strCategory })
        else Result.failure(Exception("HTTP ${r.code()}"))
    } catch (e: Exception) { Result.failure(e) }

    // ── Cocktails ─────────────────────────────────────────────────────────────

    suspend fun getCocktails(
        category: String? = null,
        search:   String? = null,
        page:     Int     = 0,
        pageSize: Int     = 20,
    ): Result<List<CocktailDto>> = try {
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

    private fun localPage(category: String?, search: String?, page: Int, pageSize: Int): List<CocktailDto> {
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

    suspend fun getCocktail(id: String): Result<CocktailDto> = try {
        val r = api.getCocktail(id)
        if (r.isSuccessful) Result.success(r.body()!!)
        else localCocktails.find { it.id == id }
            ?.let { Result.success(it) } ?: Result.failure(Exception("Not found"))
    } catch (e: Exception) {
        localCocktails.find { it.id == id }
            ?.let { Result.success(it) } ?: Result.failure(e)
    }

    suspend fun createCocktail(cocktail: CocktailDto): Result<CocktailDto> {
        localCocktails.add(0, cocktail)
        return try {
            val r = api.createCocktail(mapCocktailDto(cocktail))
            if (r.isSuccessful) Result.success(mapCocktailEntity(r.body()!!)) else Result.success(cocktail)
        } catch (e: Exception) {
            Result.success(cocktail)
        }
    }

    private fun mapCocktailDto(cocktail: CocktailDto) =
        CocktailEntity(
            id = cocktail.id,
            strDrink = cocktail.strDrink,
            strDrinkAlternate = cocktail.strDrinkAlternate,
            strTags = cocktail.strTags,
            strCategory = cocktail.strCategory,
            strIBA = cocktail.strIBA,
            strAlcoholic = cocktail.strAlcoholic,
            strGlass = cocktail.strGlass,
            strInstructions = cocktail.strInstructions,
            strDrinkThumb = cocktail.strDrinkThumb,
            strCreativeCommonsConfirmed = cocktail.strCreativeCommonsConfirmed,
            dateModified = cocktail.dateModified,
            strIngredient = cocktail.strIngredient
        )

    private fun mapCocktailEntity(cocktail: CocktailEntity) =
        CocktailDto(
            id = cocktail.id,
            strDrink = cocktail.strDrink,
            strDrinkAlternate = cocktail.strDrinkAlternate,
            strTags = cocktail.strTags,
            strCategory = cocktail.strCategory,
            strIBA = cocktail.strIBA,
            strAlcoholic = cocktail.strAlcoholic,
            strGlass = cocktail.strGlass,
            strInstructions = cocktail.strInstructions,
            strDrinkThumb = cocktail.strDrinkThumb,
            strCreativeCommonsConfirmed = cocktail.strCreativeCommonsConfirmed,
            dateModified = cocktail.dateModified,
            isFavorite = false,
            strIngredient = cocktail.strIngredient
        )

    suspend fun updateCocktail(cocktail: CocktailDto): Result<CocktailDto> {
        val idx = localCocktails.indexOfFirst { it.id == cocktail.id }
        if (idx >= 0) localCocktails[idx] = cocktail
        val favIdx = localFavorites.indexOfFirst { it.id == cocktail.id }
        if (favIdx >= 0) localFavorites[favIdx] = cocktail
        return try {
            val r = api.updateCocktail(cocktail.id,mapCocktailDto(cocktail))
            if (r.isSuccessful) Result.success(mapCocktailEntity(r.body()!!)) else Result.success(cocktail)
        } catch (e: Exception) { Result.success(cocktail) }
    }

    suspend fun deleteCocktail(id: String): Result<Unit> {
        localCocktails.removeAll { it.id == id }
        localFavorites.removeAll { it.id == id }
        return try { api.deleteCocktail(id); Result.success(Unit) }
        catch (e: Exception) { Result.success(Unit) }
    }

    // ── Favorites — full objects from backend, with pagination ────────────────

    suspend fun getFavorites(
        page:     Int = 0,
        search:   String? = null,
        pageSize: Int = 20,
    ): Result<List<CocktailDto>> = try {
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

    suspend fun addFavorite(cocktail: CocktailDto): Result<CocktailDto> = try {
        val idx = localCocktails.indexOfFirst { it.id == cocktail.id }
        if (idx >= 0) localCocktails[idx] = cocktail.copy(isFavorite = true)
        api.addFavorite(cocktail.id); Result.success(localCocktails[idx])
    } catch (e: Exception) { Result.success(cocktail) }

    suspend fun removeFavorite(cocktail: CocktailDto): Result<CocktailDto> {
        val idx = localCocktails.indexOfFirst { it.id == cocktail.id }
        if (idx >= 0) localCocktails[idx] = cocktail.copy(isFavorite = false)
        localFavorites.removeAll { it.id == cocktail.id }
        return try { api.removeFavorite(cocktail.id); Result.success(localCocktails[idx]) }
        catch (e: Exception) { Result.success(cocktail) }
    }
}
