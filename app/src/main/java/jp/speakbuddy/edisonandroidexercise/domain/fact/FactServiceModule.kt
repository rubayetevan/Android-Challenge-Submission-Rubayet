package jp.speakbuddy.edisonandroidexercise.domain.fact

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class FactServiceModule {
    @Binds
    @ViewModelScoped
    abstract fun bindFactService(factServiceImpl: FactServiceImpl): FactService

}