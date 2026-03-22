package com.drinkeiro.viewmodel;

import com.drinkeiro.data.repository.MachineRepository;
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
public final class MachineViewModel_Factory implements Factory<MachineViewModel> {
  private final Provider<MachineRepository> repoProvider;

  private final Provider<SessionRepository> sessionProvider;

  public MachineViewModel_Factory(Provider<MachineRepository> repoProvider,
      Provider<SessionRepository> sessionProvider) {
    this.repoProvider = repoProvider;
    this.sessionProvider = sessionProvider;
  }

  @Override
  public MachineViewModel get() {
    return newInstance(repoProvider.get(), sessionProvider.get());
  }

  public static MachineViewModel_Factory create(Provider<MachineRepository> repoProvider,
      Provider<SessionRepository> sessionProvider) {
    return new MachineViewModel_Factory(repoProvider, sessionProvider);
  }

  public static MachineViewModel newInstance(MachineRepository repo, SessionRepository session) {
    return new MachineViewModel(repo, session);
  }
}
