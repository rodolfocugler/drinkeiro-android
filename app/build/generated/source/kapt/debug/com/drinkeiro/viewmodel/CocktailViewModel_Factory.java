package com.drinkeiro.viewmodel;

import com.drinkeiro.data.repository.CocktailRepository;
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
public final class CocktailViewModel_Factory implements Factory<CocktailViewModel> {
  private final Provider<CocktailRepository> repoProvider;

  public CocktailViewModel_Factory(Provider<CocktailRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public CocktailViewModel get() {
    return newInstance(repoProvider.get());
  }

  public static CocktailViewModel_Factory create(Provider<CocktailRepository> repoProvider) {
    return new CocktailViewModel_Factory(repoProvider);
  }

  public static CocktailViewModel newInstance(CocktailRepository repo) {
    return new CocktailViewModel(repo);
  }
}
