package jp.speakbuddy.edisonandroidexercise.data.fact.repository

import jp.speakbuddy.edisonandroidexercise.core.Result
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.models.FactResponse
import kotlinx.coroutines.flow.Flow

interface FactRepository {
    fun getNewFact(): Flow<Result<FactResponse>>
}