package com.vani.flickrimages.domain


sealed class AsyncOperations<out T, out E> {
    data object Loading:AsyncOperations<Nothing,Nothing>()
    data class Success<T>(val data: T): AsyncOperations<T,Nothing>()
    data class Error<E>(val message: E): AsyncOperations<Nothing, E>()
}