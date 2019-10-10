package com.looke.avplayback.core.domain

sealed class ScreenState<out T> {
    data class Success<T>(val data: T) : ScreenState<T>()
    data class Error(val message: String? = null) : ScreenState<Nothing>()
}
