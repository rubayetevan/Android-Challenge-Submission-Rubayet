package jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.network

import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.models.FactResponse
import retrofit2.Response
import retrofit2.http.GET

interface FactRetrofitService {
    @GET("fact")
    suspend fun getFact(): Response<FactResponse>
}