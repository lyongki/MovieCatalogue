package com.dicoding.moviecatalogue.core.data

import com.dicoding.moviecatalogue.core.data.source.remote.api.ApiResponse
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType>{
    private var result: Flow<Resource<ResultType>> = flow{
        emit(Resource.Loading())
        val dbSource = loadFromDB().first()

        if (shouldFetch(dbSource)){
            emit(Resource.Loading())
            when(val apiResponse = createCall().first()){
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.body)
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }

                is ApiResponse.Empty -> {
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }

                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(Resource.Error<ResultType>(apiResponse.message))
                }
            }

        }
        else{
            emitAll(loadFromDB().map { Resource.Success(it) })
        }
    }

    abstract suspend fun saveCallResult(data: RequestType?)

    abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    abstract fun shouldFetch(data: ResultType): Boolean

    abstract fun loadFromDB(): Flow<ResultType>

    private fun onFetchFailed() {}

    fun asFlow(): Flow<Resource<ResultType>> = result
}