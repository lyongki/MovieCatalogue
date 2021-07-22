package com.dicoding.moviecatalogue.core.domain.repository

import com.dicoding.moviecatalogue.core.data.Resource
import com.dicoding.moviecatalogue.core.domain.model.Comment
import com.dicoding.moviecatalogue.core.domain.model.Movie
import com.dicoding.moviecatalogue.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface IFilmRepository {
    fun getMovies(): Flow<Resource<List<Movie>>>

    fun getTvShows(): Flow<Resource<List<TvShow>>>

    fun getDetailTvShow(id:String, tvShowId: String): Flow<Resource<TvShow>>

    fun getDetailMovie(id: String, movieId: String): Flow<Resource<Movie>>

    fun getFavoriteMovies(): Flow<List<Movie>>

    fun getFavoriteTvShows(): Flow<List<TvShow>>

    fun getSortMovies(favorited: Boolean, sort:String): Flow<List<Movie>>

    fun getSortTvShows(favorited: Boolean, sort:String): Flow<List<TvShow>>

    fun updateMovie(movie: Movie)

    fun updateTvShow(tvShow: TvShow)

    fun getComment(filmType: String, filmId: String): Flow<List<Comment>>

    fun insertComment(comment: Comment)
}