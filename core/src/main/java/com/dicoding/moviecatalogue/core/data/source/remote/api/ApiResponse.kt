package com.dicoding.moviecatalogue.core.data.source.remote.api

sealed class ApiResponse<out R> {
    data class Success<out T>(val body: T) : ApiResponse<T>()

    data class Error(val message: String?) : ApiResponse<Nothing>()

    object Empty : ApiResponse<Nothing>()

}