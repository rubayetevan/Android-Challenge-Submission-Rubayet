package jp.speakbuddy.edisonandroidexercise.data.fact.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FactRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindFactRepository(factRepositoryImpl: FactRepositoryImpl): FactRepository
}