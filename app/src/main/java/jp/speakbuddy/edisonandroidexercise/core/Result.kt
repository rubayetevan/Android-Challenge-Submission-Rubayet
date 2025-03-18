package jp.speakbuddy.edisonandroidexercise.core

sealed class Result<out T> {
    data object Loading : Result<Nothing>()

    data class Success<out T>(val data: T) : Result<T>()

    data class Error(val cause: ResultError) : Result<Nothing>() {
        constructor(code: Int, message: String) : this(ResultError(code, message))

        val code: Int get() = cause.code
        val message: String get() = cause.message
    }

    fun <R> map(transform: (T) -> R): Result<R> = when (this) {
        is Loading -> Loading
        is Success -> Success(transform(data))
        is Error -> this
    }
}