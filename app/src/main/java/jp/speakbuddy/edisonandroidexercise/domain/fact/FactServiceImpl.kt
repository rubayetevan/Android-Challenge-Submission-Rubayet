package jp.speakbuddy.edisonandroidexercise.domain.fact

import jp.speakbuddy.edisonandroidexercise.core.Result
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.models.FactResponse
import jp.speakbuddy.edisonandroidexercise.data.fact.repository.FactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FactServiceImpl @Inject constructor(private val factRepository: FactRepository) :
    FactService {
    override fun getNewFact(): Flow<Result<FactResponse>> = factRepository.getNewFact().onEach {
        if (it is Result.Success) {
            factRepository.saveFact(it.data)
        }
    }

    override suspend fun getLastFact(): FactResponse? = factRepository.getLastFact().first()
}