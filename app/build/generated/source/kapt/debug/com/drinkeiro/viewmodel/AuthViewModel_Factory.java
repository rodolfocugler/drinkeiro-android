package com.drinkeiro.viewmodel;

import com.drinkeiro.data.api.DrinkeiroApi;
import com.drinkeiro.data.repository.SessionRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class AuthViewModel_Factory implements Factory<AuthViewModel> {
  private final Provider<DrinkeiroApi> apiProvider;

  private final Provider<SessionRepository> sessionProvider;

  public AuthViewModel_Factory(Provider<DrinkeiroApi> apiProvider,
      Provider<SessionRepository> sessionProvider) {
    this.apiProvider = apiProvider;
    this.sessionProvider = sessionProvider;
  }

  @Override
  public AuthViewModel get() {
    return newInstance(apiProvider.get(), sessionProvider.get());
  }

  public static AuthViewModel_Factory create(Provider<DrinkeiroApi> apiProvider,
      Provider<SessionRepository> sessionProvider) {
    return new AuthViewModel_Factory(apiProvider, sessionProvider);
  }

  public static AuthViewModel newInstance(DrinkeiroApi api, SessionRepository session) {
    return new AuthViewModel(api, session);
  }
}
