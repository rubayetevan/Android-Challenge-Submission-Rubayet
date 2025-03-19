package jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource

import jp.speakbuddy.edisonandroidexercise.core.Result
import jp.speakbuddy.edisonandroidexercise.core.transformToResult
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.models.FactResponse
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.network.FactRetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FactRemoteDataSourceImpl @Inject constructor(
    private val factRetrofitService: FactRetrofitService,
    private val coroutineScope: CoroutineScope
) : FactRemoteDataSource {
    override fun getNewFact(): Flow<Result<FactResponse>> {
       return transformToResult(coroutineScope.coroutineContext) {
           factRetrofitService.getFact()
       }
    }
}