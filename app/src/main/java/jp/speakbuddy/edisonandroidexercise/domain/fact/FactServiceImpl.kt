package jp.speakbuddy.edisonandroidexercise.domain.fact

import jp.speakbuddy.edisonandroidexercise.core.Result
import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.models.FactResponse
import jp.speakbuddy.edisonandroidexercise.data.fact.repository.FactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FactServiceImpl @Inject constructor(private val factRepository: FactRepository) : FactService {
    override fun getNewFact(): Flow<Result<FactResponse>>  = factRepository.getNewFact()
}