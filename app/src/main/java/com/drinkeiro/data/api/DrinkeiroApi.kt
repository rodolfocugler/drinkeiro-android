package com.drinkeiro.data.api

import com.drinkeiro.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface DrinkeiroApi {

    // ── Auth ─────────────────────────────────────────────────────────────────

    @POST("auth/google")
    suspend fun loginWithGoogle(
        @Body body: GoogleAuthRequest,
    ): Response<AuthResponse>

    // ── Cocktails ─────────────────────────────────────────────────────────────

    /** Get all cocktails (optionally filtered by category) */
//    @GET("drinks")
//    suspend fun getCocktails(
//        @Query("filter") category: String? = null,
//    ): Response<ApiList<Cocktail>>

    @GET("drinks")
    suspend fun getCocktails(
        @Query("filter") category: String? = null,
    ): Response<List<Cocktail>>

    /** Get a single cocktail by id */
    @GET("drinks/{id}")
    suspend fun getCocktail(
        @Path("id") id: String,
    ): Response<Cocktail>

    /** Create a new cocktail recipe */
    @POST("drinks")
    suspend fun createCocktail(
        @Body cocktail: Cocktail,
    ): Response<Cocktail>

    /** Update an existing cocktail recipe */
    @PUT("drinks/{id}")
    suspend fun updateCocktail(
        @Path("id") id: String,
        @Body cocktail: Cocktail,
    ): Response<Cocktail>

    /** Delete a cocktail recipe */
    @DELETE("drinks/{id}")
    suspend fun deleteCocktail(
        @Path("id") id: String,
    ): Response<Unit>

    // ── Favorites ─────────────────────────────────────────────────────────────

    @GET("drinks/favorites")
    suspend fun getFavorites(): Response<ApiList<Cocktail>>

    @PUT("drinks/favorites/{idDrink}")
    suspend fun addFavorite(
        @Path("idDrink") idDrink: String,
    ): Response<Unit>

    @DELETE("favorites/{idDrink}")
    suspend fun removeFavorite(
        @Path("idDrink") idDrink: String,
    ): Response<Unit>

    // ── Brew ──────────────────────────────────────────────────────────────────

    @POST("machines/{machineId}/brew")
    suspend fun brew(
        @Path("machineId") machineId: String,
        @Body request: BrewRequest,
    ): Response<BrewResponse>

    // ── History ───────────────────────────────────────────────────────────────

    @GET("machines/{machineId}/history")
    suspend fun getHistory(
        @Path("machineId") machineId: String,
    ): Response<ApiList<HistoryEntry>>

    // ── Machines ──────────────────────────────────────────────────────────────

    @GET("machines")
    suspend fun getMachines(): Response<ApiList<Machine>>

    @POST("machines")
    suspend fun createMachine(
        @Body machine: Machine,
    ): Response<Machine>

    @PUT("machines/{id}")
    suspend fun updateMachine(
        @Path("id") id: String,
        @Body machine: Machine,
    ): Response<Machine>

    @DELETE("machines/{id}")
    suspend fun deleteMachine(
        @Path("id") id: String,
    ): Response<Unit>

    /** Add a collaborator by email */
    @POST("machines/{id}/collaborators")
    suspend fun addCollaborator(
        @Path("id") machineId: String,
        @Query("email") email: String,
    ): Response<Machine>

    @DELETE("machines/{id}/collaborators/{email}")
    suspend fun removeCollaborator(
        @Path("id") machineId: String,
        @Path("email") email: String,
    ): Response<Machine>

    // ── Pumps ─────────────────────────────────────────────────────────────────

    @GET("machines/{machineId}/pumps")
    suspend fun getPumps(
        @Path("machineId") machineId: String,
    ): Response<ApiList<Pump>>

    @POST("machines/{machineId}/pumps")
    suspend fun createPump(
        @Path("machineId") machineId: String,
        @Body pump: Pump,
    ): Response<Pump>

    @PUT("machines/{machineId}/pumps/{pumpNumber}")
    suspend fun updatePump(
        @Path("machineId") machineId: String,
        @Path("pumpNumber") pumpNumber: Int,
        @Body pump: Pump,
    ): Response<Pump>

    @DELETE("machines/{machineId}/pumps/{pumpNumber}")
    suspend fun deletePump(
        @Path("machineId") machineId: String,
        @Path("pumpNumber") pumpNumber: Int,
    ): Response<Unit>

    /** Trigger pump for 10 seconds */
    @POST("machines/{machineId}/pumps/{pumpNumber}/trigger")
    suspend fun triggerPump(
        @Path("machineId") machineId: String,
        @Path("pumpNumber") pumpNumber: Int,
        @Body request: PumpTriggerRequest = PumpTriggerRequest(),
    ): Response<Unit>
}
