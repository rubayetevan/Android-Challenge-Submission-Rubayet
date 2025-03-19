package jp.speakbuddy.edisonandroidexercise.domain.fact

import jp.speakbuddy.edisonandroidexercise.core.Result
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.models.FactResponse
import kotlinx.coroutines.flow.Flow

interface FactService {
    fun getNewFact(): Flow<Result<FactResponse>>
    suspend fun getLastFact(): FactResponse?
}