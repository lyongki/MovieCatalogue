package com.dicoding.moviecatalogue.core.data.source.remote

import android.util.Log
import com.dicoding.moviecatalogue.core.BuildConfig
import com.dicoding.moviecatalogue.core.data.source.remote.api.ApiResponse
import com.dicoding.moviecatalogue.core.data.source.remote.api.ApiService
import com.dicoding.moviecatalogue.core.data.source.remote.response.DetailMovieResponse
import com.dicoding.moviecatalogue.core.data.source.remote.response.DetailTvShowResponse
import com.dicoding.moviecatalogue.core.data.source.remote.response.MoviesResponse
import com.dicoding.moviecatalogue.core.data.source.remote.response.TvShowsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getMovies(): Flow<ApiResponse<MoviesResponse>> {
        return flow{
            try{
                val response = apiService.getMovies(BuildConfig.API_KEY)
                if(response.results.isEmpty()) emit(ApiResponse.Empty)
                else emit(ApiResponse.Success(response))
            }catch (e: Exception){
                emit(ApiResponse.Error(REQUEST_ERROR))
                debugError(e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTvShows() : Flow<ApiResponse<TvShowsResponse>> {
        return flow{
            try{
                val response = apiService.getTvShows(BuildConfig.API_KEY)
                if(response.results.isEmpty()) emit(ApiResponse.Empty)
                else emit(ApiResponse.Success(response))
            }catch (e: Exception){
                emit(ApiResponse.Error(REQUEST_ERROR))
                debugError(e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailMovie(movieId: String): Flow<ApiResponse<DetailMovieResponse>> {
        return flow{
            try{
                val response = apiService.getDetailMovie(movieId, BuildConfig.API_KEY)
                emit(ApiResponse.Success(response))
            }catch (e: Exception){
                emit(ApiResponse.Error(REQUEST_ERROR))
                debugError(e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailTvShow(tvShowId: String): Flow<ApiResponse<DetailTvShowResponse>>{
        return flow{
            try{
                val response = apiService.getDetailTvShow(tvShowId, BuildConfig.API_KEY)
                emit(ApiResponse.Success(response))
            }catch (e: Exception){
                emit(ApiResponse.Error(REQUEST_ERROR))
                debugError(e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun debugError(message: String) {
        Log.d(
            RemoteDataSource::class.java.simpleName,
            message)
    }

    companion object {
        private const val REQUEST_ERROR = "Can't connect to server"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }
}