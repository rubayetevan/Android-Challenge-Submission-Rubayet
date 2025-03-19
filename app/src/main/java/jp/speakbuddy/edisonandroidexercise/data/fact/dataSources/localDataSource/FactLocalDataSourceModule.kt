package jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.localDataSource

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FactLocalDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindFactLocalDataSource(localDataSourceImpl: FactLocalDataSourceImpl): FactLocalDataSource

}