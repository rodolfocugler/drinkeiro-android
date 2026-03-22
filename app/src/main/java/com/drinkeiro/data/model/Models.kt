package com.drinkeiro.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// ── Cocktail ─────────────────────────────────────────────────────────────────

@Serializable
data class Cocktail(
    @SerialName("idDrink")                      val idDrink: String,
    @SerialName("strDrink")                     val strDrink: String,
    @SerialName("strDrinkAlternate")            val strDrinkAlternate: String?       = null,
    @SerialName("strTags")                      val strTags: String?                 = null,
    @SerialName("strVideo")                     val strVideo: String?                = null,
    @SerialName("strCategory")                  val strCategory: String,
    @SerialName("strIBA")                       val strIBA: String?                  = null,
    @SerialName("strAlcoholic")                 val strAlcoholic: String,
    @SerialName("strGlass")                     val strGlass: String,
    @SerialName("strInstructions")              val strInstructions: String?         = null,
    @SerialName("strInstructionsES")            val strInstructionsES: String?       = null,
    @SerialName("strInstructionsDE")            val strInstructionsDE: String?       = null,
    @SerialName("strInstructionsFR")            val strInstructionsFR: String?       = null,
    @SerialName("strInstructionsIT")            val strInstructionsIT: String?       = null,
    @SerialName("strInstructionsZH-HANS")       val strInstructionsZhHans: String?   = null,
    @SerialName("strInstructionsZH-HANT")       val strInstructionsZhHant: String?   = null,
    @SerialName("strDrinkThumb")                val strDrinkThumb: String?           = null,
    @SerialName("strImageSource")               val strImageSource: String?          = null,
    @SerialName("strImageAttribution")          val strImageAttribution: String?     = null,
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
    @SerialName("pumpNumber")  val pumpNumber: Int,
    @SerialName("ingredient")  val ingredient: String,
    @SerialName("flowRate")    val flowRate: Double,          // ml/s
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
    @SerialName("token")     val token: String,
    @SerialName("userId")    val userId: String,
    @SerialName("name")      val name: String,
    @SerialName("email")     val email: String,
    @SerialName("photoUrl")  val photoUrl: String? = null,
)

// ── Generic wrapper ───────────────────────────────────────────────────────────

@Serializable
data class ApiList<T>(
    @SerialName("data")  val data: List<T>,
    @SerialName("total") val total: Int? = null,
)
