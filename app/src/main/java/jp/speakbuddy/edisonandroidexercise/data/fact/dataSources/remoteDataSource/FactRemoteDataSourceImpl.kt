package jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource

import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.network.FactRetrofitService
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class FactRemoteDataSourceImpl @Inject constructor(
    private val factRetrofitService: FactRetrofitService,
    private val coroutineScope: CoroutineScope
) : FactRemoteDataSource {
}