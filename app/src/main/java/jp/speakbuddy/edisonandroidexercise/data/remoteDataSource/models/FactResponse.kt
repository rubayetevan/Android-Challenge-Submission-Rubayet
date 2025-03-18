package jp.speakbuddy.edisonandroidexercise.data.remoteDataSource.models

import kotlinx.serialization.Serializable

@Serializable
data class FactResponse(
    val fact: String,
    val length: Int
)
