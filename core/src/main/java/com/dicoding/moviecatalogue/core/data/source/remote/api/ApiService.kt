package com.dicoding.moviecatalogue.core.data.source.remote.api

import com.dicoding.moviecatalogue.core.data.source.remote.response.DetailMovieResponse
import com.dicoding.moviecatalogue.core.data.source.remote.response.DetailTvShowResponse
import com.dicoding.moviecatalogue.core.data.source.remote.response.MoviesResponse
import com.dicoding.moviecatalogue.core.data.source.remote.response.TvShowsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/9312/recommendations")
    suspend fun getMovies(@Query("api_key") apiKey: String) : MoviesResponse

    @GET("tv/1668/recommendations")
    suspend fun getTvShows(@Query("api_key") apiKey: String) : TvShowsResponse

    @GET("movie/{id}")
    suspend fun getDetailMovie(
        @Path("id") id: String,
        @Query("api_key") apiKey: String
    ) : DetailMovieResponse

    @GET("tv/{id}")
    suspend fun getDetailTvShow(
        @Path("id") id: String,
        @Query("api_key") apiKey: String
    ): DetailTvShowResponse
}