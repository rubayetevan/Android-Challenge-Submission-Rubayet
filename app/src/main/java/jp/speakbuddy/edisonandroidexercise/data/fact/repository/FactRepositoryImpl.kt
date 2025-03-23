package jp.speakbuddy.edisonandroidexercise.data.fact.repository

import jp.speakbuddy.edisonandroidexercise.core.Result
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.localDataSource.FactLocalDataSource
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.FactRemoteDataSource
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.models.FactResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FactRepositoryImpl @Inject constructor(
    private val factRemoteDataSource: FactRemoteDataSource,
    private val factLocalDataSource: FactLocalDataSource
) : FactRepository {
    override fun getNewFact(): Flow<Result<FactResponse>> =
        factRemoteDataSource.getNewFact().onEach {
            if (it is Result.Success) {
                saveFact(it.data)
            }
        }

    override suspend fun getLastFact(): FactResponse? = factLocalDataSource.getLastFact().first()

    private suspend fun saveFact(factResponse: FactResponse) {
        factLocalDataSource.saveFact(factResponse)
    }
}