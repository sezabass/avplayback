package com.looke.avplayback.core.domain

import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException

sealed class ResponseState<out T> {
    data class Success<T>(val data: T) : ResponseState<T>()
    data class ServerError(
        val code: Int,
        val message: String,
        val errorBody: ResponseBody?
    ) : ResponseState<Nothing>()

    data class GeneralError(val e: Throwable) : ResponseState<Nothing>()

    fun <M> map(mapper: (originalData: T) -> M): ResponseState<M> = when (this) {
        is Success -> Success(mapper(data))
        is ServerError -> ServerError(code, message, errorBody)
        is GeneralError -> GeneralError(e)
    }
}

fun <T> Response<T>.toResponseState(): ResponseState<T> = try {
    if (isSuccessful) {
        ResponseState.Success(body()!!)
    } else {
        ResponseState.ServerError(code(), message(), errorBody())
    }
} catch (e: IOException) {
    ResponseState.GeneralError(e)
}
