package com.dicoding.moviecatalogue.core.data.source.local.room

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.dicoding.moviecatalogue.core.data.source.local.entity.CommentEntity
import com.dicoding.moviecatalogue.core.data.source.local.entity.MovieEntity
import com.dicoding.moviecatalogue.core.data.source.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {
    @Query("SELECT * FROM movieentities")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM tvshowentities")
    fun getTvShows(): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM movieentities WHERE favorited = 1")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM tvshowentities WHERE favorited = 1")
    fun getFavoriteTvShows(): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM movieentities WHERE id = :movieId")
    fun getMovieById(movieId: String):Flow<MovieEntity>

    @Query("SELECT * FROM tvshowentities WHERE id = :tvShowId")
    fun getTvShowById(tvShowId: String):Flow<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(tvShows: List<TvShowEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Update
    fun updateTvShow(tvShow: TvShowEntity)

    @RawQuery(observedEntities = [MovieEntity::class])
    fun getSortMovies(query: SupportSQLiteQuery): Flow<List<MovieEntity>>

    @RawQuery(observedEntities = [TvShowEntity::class])
    fun getSortTvShows(query: SupportSQLiteQuery): Flow<List<TvShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComment(commentEntity: CommentEntity)

    @Query("SELECT * FROM commententities WHERE film_type = :filmTyoe AND film_id = :filmId")
    fun getComment(filmTyoe: String, filmId: String):Flow<List<CommentEntity>>
}