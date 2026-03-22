package com.drinkeiro.data.api;

import com.drinkeiro.data.model.*;
import retrofit2.Response;
import retrofit2.http.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\bf\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u001e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00032\b\b\u0001\u0010\u000b\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\fJ(\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u001e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00032\b\b\u0001\u0010\u0014\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0015J\u001e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0017\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0018J(\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u001b\u001a\u00020\u001aH\u00a7@\u00a2\u0006\u0002\u0010\u001cJ\u001e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\n0\u00032\b\b\u0001\u0010\u001e\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\fJ\u001e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\n0\u00032\b\b\u0001\u0010\u001e\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\fJ(\u0010 \u001a\b\u0012\u0004\u0012\u00020\n0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010!\u001a\u00020\"H\u00a7@\u00a2\u0006\u0002\u0010#J\u001e\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00130\u00032\b\b\u0001\u0010\u001e\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\fJ&\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130&0\u00032\n\b\u0003\u0010\'\u001a\u0004\u0018\u00010\u0006H\u00a7@\u00a2\u0006\u0002\u0010\fJ\u001a\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130&0\u0003H\u00a7@\u00a2\u0006\u0002\u0010)J$\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020+0&0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\fJ\u001a\u0010,\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040&0\u0003H\u00a7@\u00a2\u0006\u0002\u0010)J$\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0&0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\fJ\u001e\u0010.\u001a\b\u0012\u0004\u0012\u00020/0\u00032\b\b\u0001\u00100\u001a\u000201H\u00a7@\u00a2\u0006\u0002\u00102J(\u00103\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u001e\u00104\u001a\b\u0012\u0004\u0012\u00020\n0\u00032\b\b\u0001\u0010\u000b\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\fJ2\u00105\u001a\b\u0012\u0004\u0012\u00020\n0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010!\u001a\u00020\"2\b\b\u0003\u0010\u000f\u001a\u000206H\u00a7@\u00a2\u0006\u0002\u00107J(\u00108\u001a\b\u0012\u0004\u0012\u00020\u00130\u00032\b\b\u0001\u0010\u001e\u001a\u00020\u00062\b\b\u0001\u0010\u0014\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u00109J(\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u001e\u001a\u00020\u00062\b\b\u0001\u0010\u0017\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010;J2\u0010<\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010!\u001a\u00020\"2\b\b\u0001\u0010\u001b\u001a\u00020\u001aH\u00a7@\u00a2\u0006\u0002\u0010=\u00a8\u0006>"}, d2 = {"Lcom/drinkeiro/data/api/DrinkeiroApi;", "", "addCollaborator", "Lretrofit2/Response;", "Lcom/drinkeiro/data/model/Machine;", "machineId", "", "email", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addFavorite", "", "idDrink", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "brew", "Lcom/drinkeiro/data/model/BrewResponse;", "request", "Lcom/drinkeiro/data/model/BrewRequest;", "(Ljava/lang/String;Lcom/drinkeiro/data/model/BrewRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createCocktail", "Lcom/drinkeiro/data/model/Cocktail;", "cocktail", "(Lcom/drinkeiro/data/model/Cocktail;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createMachine", "machine", "(Lcom/drinkeiro/data/model/Machine;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createPump", "Lcom/drinkeiro/data/model/Pump;", "pump", "(Ljava/lang/String;Lcom/drinkeiro/data/model/Pump;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteCocktail", "id", "deleteMachine", "deletePump", "pumpNumber", "", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCocktail", "getCocktails", "Lcom/drinkeiro/data/model/ApiList;", "category", "getFavorites", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getHistory", "Lcom/drinkeiro/data/model/HistoryEntry;", "getMachines", "getPumps", "loginWithGoogle", "Lcom/drinkeiro/data/model/AuthResponse;", "body", "Lcom/drinkeiro/data/model/GoogleAuthRequest;", "(Lcom/drinkeiro/data/model/GoogleAuthRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "removeCollaborator", "removeFavorite", "triggerPump", "Lcom/drinkeiro/data/model/PumpTriggerRequest;", "(Ljava/lang/String;ILcom/drinkeiro/data/model/PumpTriggerRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateCocktail", "(Ljava/lang/String;Lcom/drinkeiro/data/model/Cocktail;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateMachine", "(Ljava/lang/String;Lcom/drinkeiro/data/model/Machine;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updatePump", "(Ljava/lang/String;ILcom/drinkeiro/data/model/Pump;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface DrinkeiroApi {
    
    @retrofit2.http.POST(value = "auth/google")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object loginWithGoogle(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.model.GoogleAuthRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.drinkeiro.data.model.AuthResponse>> $completion);
    
    /**
     * Get all cocktails (optionally filtered by category)
     */
    @retrofit2.http.GET(value = "cocktails")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCocktails(@retrofit2.http.Query(value = "category")
    @org.jetbrains.annotations.Nullable()
    java.lang.String category, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.drinkeiro.data.model.ApiList<com.drinkeiro.data.model.Cocktail>>> $completion);
    
    /**
     * Get a single cocktail by id
     */
    @retrofit2.http.GET(value = "cocktails/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCocktail(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.drinkeiro.data.model.Cocktail>> $completion);
    
    /**
     * Create a new cocktail recipe
     */
    @retrofit2.http.POST(value = "cocktails")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createCocktail(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.model.Cocktail cocktail, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.drinkeiro.data.model.Cocktail>> $completion);
    
    /**
     * Update an existing cocktail recipe
     */
    @retrofit2.http.PUT(value = "cocktails/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateCocktail(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.model.Cocktail cocktail, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.drinkeiro.data.model.Cocktail>> $completion);
    
    /**
     * Delete a cocktail recipe
     */
    @retrofit2.http.DELETE(value = "cocktails/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteCocktail(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    @retrofit2.http.GET(value = "favorites")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getFavorites(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.drinkeiro.data.model.ApiList<com.drinkeiro.data.model.Cocktail>>> $completion);
    
    @retrofit2.http.PUT(value = "favorites/{idDrink}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object addFavorite(@retrofit2.http.Path(value = "idDrink")
    @org.jetbrains.annotations.NotNull()
    java.lang.String idDrink, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    @retrofit2.http.DELETE(value = "favorites/{idDrink}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object removeFavorite(@retrofit2.http.Path(value = "idDrink")
    @org.jetbrains.annotations.NotNull()
    java.lang.String idDrink, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    @retrofit2.http.POST(value = "machines/{machineId}/brew")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object brew(@retrofit2.http.Path(value = "machineId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String machineId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.model.BrewRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.drinkeiro.data.model.BrewResponse>> $completion);
    
    @retrofit2.http.GET(value = "machines/{machineId}/history")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getHistory(@retrofit2.http.Path(value = "machineId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String machineId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.drinkeiro.data.model.ApiList<com.drinkeiro.data.model.HistoryEntry>>> $completion);
    
    @retrofit2.http.GET(value = "machines")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMachines(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.drinkeiro.data.model.ApiList<com.drinkeiro.data.model.Machine>>> $completion);
    
    @retrofit2.http.POST(value = "machines")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createMachine(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.model.Machine machine, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.drinkeiro.data.model.Machine>> $completion);
    
    @retrofit2.http.PUT(value = "machines/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateMachine(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.model.Machine machine, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.drinkeiro.data.model.Machine>> $completion);
    
    @retrofit2.http.DELETE(value = "machines/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteMachine(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    /**
     * Add a collaborator by email
     */
    @retrofit2.http.POST(value = "machines/{id}/collaborators")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object addCollaborator(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String machineId, @retrofit2.http.Query(value = "email")
    @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.drinkeiro.data.model.Machine>> $completion);
    
    @retrofit2.http.DELETE(value = "machines/{id}/collaborators/{email}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object removeCollaborator(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String machineId, @retrofit2.http.Path(value = "email")
    @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.drinkeiro.data.model.Machine>> $completion);
    
    @retrofit2.http.GET(value = "machines/{machineId}/pumps")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPumps(@retrofit2.http.Path(value = "machineId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String machineId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.drinkeiro.data.model.ApiList<com.drinkeiro.data.model.Pump>>> $completion);
    
    @retrofit2.http.POST(value = "machines/{machineId}/pumps")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createPump(@retrofit2.http.Path(value = "machineId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String machineId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.model.Pump pump, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.drinkeiro.data.model.Pump>> $completion);
    
    @retrofit2.http.PUT(value = "machines/{machineId}/pumps/{pumpNumber}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updatePump(@retrofit2.http.Path(value = "machineId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String machineId, @retrofit2.http.Path(value = "pumpNumber")
    int pumpNumber, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.model.Pump pump, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.drinkeiro.data.model.Pump>> $completion);
    
    @retrofit2.http.DELETE(value = "machines/{machineId}/pumps/{pumpNumber}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deletePump(@retrofit2.http.Path(value = "machineId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String machineId, @retrofit2.http.Path(value = "pumpNumber")
    int pumpNumber, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    /**
     * Trigger pump for 10 seconds
     */
    @retrofit2.http.POST(value = "machines/{machineId}/pumps/{pumpNumber}/trigger")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object triggerPump(@retrofit2.http.Path(value = "machineId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String machineId, @retrofit2.http.Path(value = "pumpNumber")
    int pumpNumber, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.model.PumpTriggerRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}