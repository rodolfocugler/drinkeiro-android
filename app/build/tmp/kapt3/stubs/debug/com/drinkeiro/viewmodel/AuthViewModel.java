package com.drinkeiro.viewmodel;

import android.content.Context;
import android.util.Log;
import androidx.credentials.CredentialManager;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.lifecycle.ViewModel;
import com.drinkeiro.BuildConfig;
import com.drinkeiro.data.api.DrinkeiroApi;
import com.drinkeiro.data.model.GoogleAuthRequest;
import com.drinkeiro.data.repository.SessionRepository;
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J(\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0013\u001a\u00020\u0011H\u0082@\u00a2\u0006\u0002\u0010\u0014J\u000e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0017R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0018"}, d2 = {"Lcom/drinkeiro/viewmodel/AuthViewModel;", "Landroidx/lifecycle/ViewModel;", "api", "Lcom/drinkeiro/data/api/DrinkeiroApi;", "session", "Lcom/drinkeiro/data/repository/SessionRepository;", "(Lcom/drinkeiro/data/api/DrinkeiroApi;Lcom/drinkeiro/data/repository/SessionRepository;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/drinkeiro/viewmodel/AuthState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "sendTokenToBackend", "", "idToken", "", "displayName", "email", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signInWithGoogle", "activityContext", "Landroid/content/Context;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AuthViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.drinkeiro.data.api.DrinkeiroApi api = null;
    @org.jetbrains.annotations.NotNull()
    private final com.drinkeiro.data.repository.SessionRepository session = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.drinkeiro.viewmodel.AuthState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.drinkeiro.viewmodel.AuthState> state = null;
    
    @javax.inject.Inject()
    public AuthViewModel(@org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.api.DrinkeiroApi api, @org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.repository.SessionRepository session) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.drinkeiro.viewmodel.AuthState> getState() {
        return null;
    }
    
    public final void signInWithGoogle(@org.jetbrains.annotations.NotNull()
    android.content.Context activityContext) {
    }
    
    private final java.lang.Object sendTokenToBackend(java.lang.String idToken, java.lang.String displayName, java.lang.String email, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}