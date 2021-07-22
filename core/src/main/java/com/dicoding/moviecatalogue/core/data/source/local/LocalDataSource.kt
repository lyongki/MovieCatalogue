package com.dicoding.moviecatalogue.core.data.source.local

import androidx.sqlite.db.SimpleSQLiteQuery
import com.dicoding.moviecatalogue.core.data.source.local.entity.CommentEntity
import com.dicoding.moviecatalogue.core.data.source.local.entity.MovieEntity
import com.dicoding.moviecatalogue.core.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.core.data.source.local.room.FilmDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val filmDao: FilmDao) {
    fun getMovies(): Flow<List<MovieEntity>> = filmDao.getMovies()

    fun getTvShows(): Flow<List<TvShowEntity>> = filmDao.getTvShows()

    fun getMovie(movieId: String): Flow<MovieEntity> = filmDao.getMovieById(movieId)

    fun getTvShow(tvShowId: String): Flow<TvShowEntity> = filmDao.getTvShowById(tvShowId)

    fun getFavoriteMovies(): Flow<List<MovieEntity>> = filmDao.getFavoriteMovies()

    fun getFavoriteTvShows(): Flow<List<TvShowEntity>> = filmDao.getFavoriteTvShows()

    suspend fun insertMovies(movies: List<MovieEntity>) = filmDao.insertMovies(movies)

    suspend fun insertTvShows(tvShows: List<TvShowEntity>) = filmDao.insertTvShows(tvShows)

    fun updateMovie(movie: MovieEntity) = filmDao.updateMovie(movie)

    fun updateTvShow(tvShow: TvShowEntity) = filmDao.updateTvShow(tvShow)

    fun getSortMovies(query: SimpleSQLiteQuery): Flow<List<MovieEntity>> =
        filmDao.getSortMovies(query)

    fun getSortTvShows(query: SimpleSQLiteQuery): Flow<List<TvShowEntity>> =
        filmDao.getSortTvShows(query)

    fun getComment(filmType: String, filmId: String): Flow<List<CommentEntity>> =
        filmDao.getComment(filmType, filmId)

    fun insertComment(commentEntity: CommentEntity) = filmDao.insertComment(commentEntity)
}