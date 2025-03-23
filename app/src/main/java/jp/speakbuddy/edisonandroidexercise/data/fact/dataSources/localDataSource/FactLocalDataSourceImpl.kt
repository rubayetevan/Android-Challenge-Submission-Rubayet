package jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.localDataSource

import androidx.datastore.core.DataStore
import jp.speakbuddy.edisonandroidexercise.FactProto
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.models.FactResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FactLocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<FactProto>,
    private val coroutineScope: CoroutineScope
) :
    FactLocalDataSource {
    override fun getLastFact(): Flow<FactResponse?> {
        return dataStore.data.map { proto ->
            if (proto == FactProto.getDefaultInstance()) {
                null
            } else {
                FactResponse(proto.fact, proto.length)
            }
        }
    }

    override suspend fun saveFact(factResponse: FactResponse): FactResponse {
        return withContext(coroutineScope.coroutineContext) {
            val factProto = async {
                dataStore.updateData { proto ->
                    proto.toBuilder()
                        .setFact(factResponse.fact)
                        .setLength(factResponse.length)
                        .build()
                }
            }.await()
            FactResponse(factProto.fact, factProto.length)
        }
    }

}