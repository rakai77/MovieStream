package com.example.moviestream.core

sealed class BaseResult<out T> {
    data class Success<T>(val data: T) : BaseResult<T>()
    data class Error(val statusCode: Int? = null, val message: String) : BaseResult<Nothing>()
}