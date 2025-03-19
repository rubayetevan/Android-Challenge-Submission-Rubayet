package jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FactRetrofitModule {

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): FactRetrofitService {
        return retrofit.create(FactRetrofitService::class.java)
    }
}