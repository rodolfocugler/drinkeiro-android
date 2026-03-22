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
public final class MachineRepository_Factory implements Factory<MachineRepository> {
  private final Provider<DrinkeiroApi> apiProvider;

  public MachineRepository_Factory(Provider<DrinkeiroApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public MachineRepository get() {
    return newInstance(apiProvider.get());
  }

  public static MachineRepository_Factory create(Provider<DrinkeiroApi> apiProvider) {
    return new MachineRepository_Factory(apiProvider);
  }

  public static MachineRepository newInstance(DrinkeiroApi api) {
    return new MachineRepository(api);
  }
}
