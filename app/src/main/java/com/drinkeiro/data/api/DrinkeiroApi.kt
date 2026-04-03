package com.drinkeiro.data.api

import com.drinkeiro.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface DrinkeiroApi {

    // ── Auth ──────────────────────────────────────────────────────────────────

    @POST("api/auth/google")
    suspend fun loginWithGoogle(@Body body: GoogleAuthRequest): Response<AuthResponse>

    @POST("api/auth/refresh")
    suspend fun refreshToken(@Body body: RefreshTokenRequest): Response<AuthResponse>

    @POST("api/auth/logout")
    suspend fun logout(): Response<Unit>

    // ── Health ────────────────────────────────────────────────────────────────

    @GET("actuator/health")
    suspend fun health(): Response<Unit>

    // ── Cocktails ─────────────────────────────────────────────────────────────

    @GET("api/cocktails")
    suspend fun getCocktails(
        @Query("category") category: String? = null,
        @Query("search")   search:   String? = null,
        @Query("page")     page:     Int     = 0,
        @Query("pageSize") pageSize: Int     = 20,
    ): Response<ApiList<CocktailDto>>

    @GET("api/cocktails/{id}")
    suspend fun getCocktail(@Path("id") id: String): Response<CocktailDto>

    @POST("api/cocktails")
    suspend fun createCocktail(@Body cocktail: CocktailEntity): Response<CocktailEntity>

    @PUT("api/cocktails/{id}")
    suspend fun updateCocktail(@Path("id") id: String, @Body cocktail: CocktailEntity): Response<CocktailEntity>

    @DELETE("api/cocktails/{id}")
    suspend fun deleteCocktail(@Path("id") id: String): Response<Unit>

    // ── Favorites — paginated list of full Cocktail objects ───────────────────

    @GET("api/cocktails/favorites")
    suspend fun getFavorites(
        @Query("page")     page:     Int = 0,
        @Query("filter")   search:   String? = null,
        @Query("pageSize") pageSize: Int = 20,
    ): Response<ApiList<CocktailDto>>

    @PATCH("api/cocktails/favorites/{id}")
    suspend fun addFavorite(@Path("id") idDrink: String): Response<Unit>

    @DELETE("api/cocktails/favorites/{id}")
    suspend fun removeFavorite(@Path("id") id: String): Response<Unit>

    // ── Brew ──────────────────────────────────────────────────────────────────

    @POST("api/machines/{machineId}/brew")
    suspend fun brew(
        @Path("machineId") machineId: String,
        @Body request: BrewRequest,
    ): Response<BrewResponse>

    // ── History ───────────────────────────────────────────────────────────────

    @GET("api/histories/machine/{machineId}")
    suspend fun getHistory(
        @Path("machineId") machineId: String,
        @Query("page")     page:      Int = 0,
        @Query("pageSize") pageSize:  Int = 20,
    ): Response<ApiList<HistoryEntry>>

    // ── Cocktail categories ───────────────────────────────────────────────────

    @GET("api/categories")
    suspend fun getCategories(): Response<List<Category>>

    // ── Machines ──────────────────────────────────────────────────────────────

    @GET("api/machines")
    suspend fun getMachines(): Response<ApiList<Machine>>

    @POST("api/machines")
    suspend fun createMachine(@Body machine: Machine): Response<Machine>

    @PUT("api/machines/{id}")
    suspend fun updateMachine(@Path("id") id: String, @Body machine: Machine): Response<Machine>

    @DELETE("api/machines/{id}")
    suspend fun deleteMachine(@Path("id") id: String): Response<Unit>

    @PUT("api/machines/{id}/email/{email}")
    suspend fun addCollaborator(
        @Path("id")    machineId: String,
        @Path("email") email: String,
    ): Response<Machine>

    @DELETE("api/machines/{id}/email/{email}")
    suspend fun removeCollaborator(
        @Path("id")    machineId: String,
        @Path("email") email: String,
    ): Response<Machine>

    // ── Pumps ─────────────────────────────────────────────────────────────────

    @GET("api/machines/{machineId}/pumps")
    suspend fun getPumps(@Path("machineId") machineId: String): Response<List<Pump>>

    @POST("api/machines/{machineId}/pumps")
    suspend fun createPump(
        @Path("machineId") machineId: String,
        @Body pump: Pump,
    ): Response<Pump>

    @PUT("api/machines/{machineId}/pumps/{pumpNumber}")
    suspend fun updatePump(
        @Path("machineId")   machineId: String,
        @Path("pumpNumber")  pumpNumber: Int,
        @Body pump: Pump,
    ): Response<Pump>

    @DELETE("api/machines/{machineId}/pumps/{pumpNumber}")
    suspend fun deletePump(
        @Path("machineId")  machineId: String,
        @Path("pumpNumber") pumpNumber: Int,
    ): Response<Unit>

    @POST("api/machines/{machineId}/pumps/{pumpNumber}/trigger")
    suspend fun triggerPump(
        @Path("machineId")  machineId: String,
        @Path("pumpNumber") pumpNumber: Int,
        @Body request: PumpTriggerRequest = PumpTriggerRequest(),
    ): Response<Unit>
}
