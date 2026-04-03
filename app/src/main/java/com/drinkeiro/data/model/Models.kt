package com.drinkeiro.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// ── Cocktail ─────────────────────────────────────────────────────────────────

@Serializable
data class CocktailDto(
    @SerialName("id")                           val id: String,
    @SerialName("strDrink")                     val strDrink: String,
    @SerialName("strDrinkAlternate")            val strDrinkAlternate: String?       = null,
    @SerialName("strTags")                      val strTags: String?                 = null,
    @SerialName("strCategory")                  val strCategory: String?             = null,
    @SerialName("strIBA")                       val strIBA: String?                  = null,
    @SerialName("strAlcoholic")                 val strAlcoholic: String?            = null,
    @SerialName("strGlass")                     val strGlass: String?                = null,
    @SerialName("strInstructions")              val strInstructions: String?         = null,
    @SerialName("strDrinkThumb")                val strDrinkThumb: String?           = null,
    @SerialName("strCreativeCommonsConfirmed")  val strCreativeCommonsConfirmed: String? = null,
    @SerialName("isFavorite")                   val isFavorite: Boolean,
    @SerialName("dateModified")                 val dateModified: String?            = null,
    @SerialName("strIngredient")                val strIngredient: List<Ingredient>  = emptyList(),
)

@Serializable
data class CocktailEntity(
    @SerialName("id")                           val id: String,
    @SerialName("strDrink")                     val strDrink: String,
    @SerialName("strDrinkAlternate")            val strDrinkAlternate: String?       = null,
    @SerialName("strTags")                      val strTags: String?                 = null,
    @SerialName("strCategory")                  val strCategory: String?             = null,
    @SerialName("strIBA")                       val strIBA: String?                  = null,
    @SerialName("strAlcoholic")                 val strAlcoholic: String?            = null,
    @SerialName("strGlass")                     val strGlass: String?                = null,
    @SerialName("strInstructions")              val strInstructions: String?         = null,
    @SerialName("strDrinkThumb")                val strDrinkThumb: String?           = null,
    @SerialName("strCreativeCommonsConfirmed")  val strCreativeCommonsConfirmed: String? = null,
    @SerialName("dateModified")                 val dateModified: String?            = null,
    @SerialName("strIngredient")                val strIngredient: List<Ingredient>  = emptyList(),
)

@Serializable
data class Ingredient(
    @SerialName("strIngredient") val strIngredient: String,
    @SerialName("strMeasure")    val strMeasure: String?    = null,
    @SerialName("order")         val order: Int,
)

// ── Category ───────────────────────────────────────────────────────────────────

@Serializable
data class Category(
    @SerialName("id")            val id: String,
    @SerialName("strCategory")   val strCategory: String,
)

// ── Machine ───────────────────────────────────────────────────────────────────

@Serializable
data class Machine(
    @SerialName("id")            val id: String,
    @SerialName("name")          val name: String,
    @SerialName("status")        val status: String,           // "online" | "offline"
    @SerialName("collaborators") val collaborators: List<String> = emptyList(),
)

// ── Pump ──────────────────────────────────────────────────────────────────────

@Serializable
data class Pump(
    @SerialName("port")                  val port: Int,
    @SerialName("name")                  val name: String,
    @SerialName("ingredientId")          val ingredientId: String,
    @SerialName("flowRateInMlPerSec")    val flowRateInMlPerSec: Double,
)

@Serializable
data class PumpTriggerRequest(
    @SerialName("durationSeconds") val durationSeconds: Int = 10,
)

// ── History ───────────────────────────────────────────────────────────────────

@Serializable
data class HistoryEntry(
    @SerialName("id")        val id: String,
    @SerialName("idDrink")   val idDrink: String,
    @SerialName("strDrink")  val strDrink: String,
    @SerialName("ts")        val ts: String,
    @SerialName("user")      val user: String,
)

// ── Brew ──────────────────────────────────────────────────────────────────────

@Serializable
data class BrewRequest(
    @SerialName("idDrink")       val idDrink: String,
    @SerialName("strIngredient") val strIngredient: List<Ingredient>,
)

@Serializable
data class BrewResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("message") val message: String? = null,
)

// ── Auth ──────────────────────────────────────────────────────────────────────

@Serializable
data class GoogleAuthRequest(
    @SerialName("idToken") val idToken: String,
)

@Serializable
data class AuthResponse(
    @SerialName("accessToken")  val accessToken: String,
    @SerialName("refreshToken") val refreshToken: String? = null,
    @SerialName("userId")       val userId: String,
    @SerialName("name")         val name: String,
    @SerialName("email")        val email: String,
    @SerialName("photoUrl")     val photoUrl: String? = null,
)

// ── Generic wrapper ───────────────────────────────────────────────────────────

@Serializable
data class ApiList<T>(
    @SerialName("content") val content: List<T>,
    @SerialName("total") val total: Int? = null,
)

// ── Token refresh ─────────────────────────────────────────────────────────────

@Serializable
data class RefreshTokenRequest(
    @SerialName("refreshToken") val refreshToken: String,
)

// ── Collaborator ──────────────────────────────────────────────────────────────

@Serializable
data class CollaboratorRequest(
    @SerialName("email") val email: String,
)
