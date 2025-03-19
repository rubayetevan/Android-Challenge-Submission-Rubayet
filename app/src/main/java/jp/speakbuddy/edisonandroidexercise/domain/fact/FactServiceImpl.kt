package jp.speakbuddy.edisonandroidexercise.domain.fact

import jp.speakbuddy.edisonandroidexercise.data.fact.repository.FactRepository
import javax.inject.Inject

class FactServiceImpl @Inject constructor(private val factRepository: FactRepository) : FactService {
}