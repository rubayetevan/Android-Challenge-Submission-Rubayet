package jp.speakbuddy.edisonandroidexercise.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import kotlin.coroutines.CoroutineContext


fun <T> transformToResult(
    context: CoroutineContext,
    block: suspend () -> Response<T>
): Flow<Result<T>> = flow {
    emit(Result.Loading)

    val response: Response<T>
    try {
        response = withContext(context) {
            block()
        }
    } catch (exception: Exception) {
        when (exception) {
            is ConnectException -> {
                emit(Result.Error(ResultError(-1, "No Internet Error: ${exception.message}")))
                return@flow
            }

            is SocketTimeoutException -> {
                emit(Result.Error(ResultError(-2, "Socket Timeout error: ${exception.message}")))
                return@flow
            }

            is IOException -> {
                emit(Result.Error(ResultError(-3, "Network error: ${exception.message}")))
                return@flow
            }

            else -> {
                emit(Result.Error(ResultError(-4, "Unexpected error: ${exception.message}")))
                return@flow
            }
        }
    }

    if (response.isSuccessful) {
        val body = response.body()
        if (body != null) {
            emit(Result.Success(body))
        } else {
            emit(Result.Error(ResultError(response.code(), "Response body is null")))
        }
    } else {
        val errorCode = response.code()
        val errorMessage = response.errorBody()?.string() ?: "Unknown error"
        emit(Result.Error(ResultError(errorCode, errorMessage)))
    }


}