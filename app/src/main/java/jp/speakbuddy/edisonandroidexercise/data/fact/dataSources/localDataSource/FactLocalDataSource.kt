package jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.localDataSource

import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.models.FactResponse
import kotlinx.coroutines.flow.Flow

interface FactLocalDataSource {
    fun getLastFact(): Flow<FactResponse?>
    fun saveFact(factResponse: FactResponse)
}