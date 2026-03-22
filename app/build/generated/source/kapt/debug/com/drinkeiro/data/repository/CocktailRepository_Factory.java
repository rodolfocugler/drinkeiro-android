package com.drinkeiro.data.repository;

import com.drinkeiro.data.api.DrinkeiroApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class CocktailRepository_Factory implements Factory<CocktailRepository> {
  private final Provider<DrinkeiroApi> apiProvider;

  public CocktailRepository_Factory(Provider<DrinkeiroApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public CocktailRepository get() {
    return newInstance(apiProvider.get());
  }

  public static CocktailRepository_Factory create(Provider<DrinkeiroApi> apiProvider) {
    return new CocktailRepository_Factory(apiProvider);
  }

  public static CocktailRepository newInstance(DrinkeiroApi api) {
    return new CocktailRepository(api);
  }
}
