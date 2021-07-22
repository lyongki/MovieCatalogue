package com.dicoding.moviecatalogue.core.data

sealed class Resource<T>(val body: T? = null, val message: String? = null) {
        class Success<T>(body: T?): Resource<T>(body)

        class Loading<T>(body: T? = null): Resource<T>(body)

        class Error<T>(message: String?, body: T? = null): Resource<T>(body, message)
}