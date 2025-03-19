package jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FactRemoteDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindFactRemoteDataSource(factRemoteDataSourceImpl: FactRemoteDataSourceImpl): FactRemoteDataSource

}