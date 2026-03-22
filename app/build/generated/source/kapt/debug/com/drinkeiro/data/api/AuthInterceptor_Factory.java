package com.drinkeiro.data.api;

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
public final class AuthInterceptor_Factory implements Factory<AuthInterceptor> {
  private final Provider<SessionRepository> sessionProvider;

  public AuthInterceptor_Factory(Provider<SessionRepository> sessionProvider) {
    this.sessionProvider = sessionProvider;
  }

  @Override
  public AuthInterceptor get() {
    return newInstance(sessionProvider.get());
  }

  public static AuthInterceptor_Factory create(Provider<SessionRepository> sessionProvider) {
    return new AuthInterceptor_Factory(sessionProvider);
  }

  public static AuthInterceptor newInstance(SessionRepository session) {
    return new AuthInterceptor(session);
  }
}
