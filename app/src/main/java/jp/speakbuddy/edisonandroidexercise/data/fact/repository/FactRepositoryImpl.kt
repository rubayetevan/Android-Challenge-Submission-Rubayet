package jp.speakbuddy.edisonandroidexercise.data.fact.repository

import jp.speakbuddy.edisonandroidexercise.core.Result
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.localDataSource.FactLocalDataSource
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.FactRemoteDataSource
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.models.FactResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FactRepositoryImpl @Inject constructor(
    private val factRemoteDataSource: FactRemoteDataSource,
    private val factLocalDataSource: FactLocalDataSource
) : FactRepository {
    override fun getNewFact(): Flow<Result<FactResponse>> = factRemoteDataSource.getNewFact()
    override fun getLastFact(): Flow<FactResponse?> = factLocalDataSource.getLastFact()

    override fun saveFact(factResponse: FactResponse) {
        factLocalDataSource.saveFact(factResponse)
    }
}