package jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.remoteDataSource.models

import kotlinx.serialization.Serializable

@Serializable
data class FactResponse(
    val fact: String,
    val length: Int
)
