package jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource

import jp.speakbuddy.edisonandroidexercise.core.Result
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.models.FactResponse
import kotlinx.coroutines.flow.Flow

interface FactRemoteDataSource {
    fun getNewFact(): Flow<Result<FactResponse>>
}