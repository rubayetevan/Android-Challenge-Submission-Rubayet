package jp.speakbuddy.edisonandroidexercise.data.fact.repository

import jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.FactRemoteDataSource
import javax.inject.Inject

class FactRepositoryImpl @Inject constructor(private val factRemoteDataSource: FactRemoteDataSource) :
    FactRepository {
}