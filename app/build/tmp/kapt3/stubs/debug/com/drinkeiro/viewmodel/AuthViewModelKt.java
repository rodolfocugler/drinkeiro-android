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

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u000e\n\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0002"}, d2 = {"TAG", "", "app_debug"})
public final class AuthViewModelKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "DrinkeiroAuth";
}