package jp.speakbuddy.edisonandroidexercise.data.remoteDataSource

import jp.speakbuddy.edisonandroidexercise.data.remoteDataSource.models.FactResponse
import retrofit2.Response
import retrofit2.http.GET

interface FactRetrofitService {
    @GET("fact")
    suspend fun getFact(): Response<FactResponse>
}