package com.dicoding.moviecatalogue.core.domain.usecase

import com.dicoding.moviecatalogue.core.data.Resource
import com.dicoding.moviecatalogue.core.domain.model.Comment
import com.dicoding.moviecatalogue.core.domain.model.Movie
import com.dicoding.moviecatalogue.core.domain.model.TvShow
import com.dicoding.moviecatalogue.core.domain.repository.IFilmRepository
import kotlinx.coroutines.flow.Flow

class FilmInteractor(private val filmRepository: IFilmRepository) : FilmUseCase {
    override fun getMovies(): Flow<Resource<List<Movie>>> =
        filmRepository.getMovies()

    override fun getTvShows(): Flow<Resource<List<TvShow>>> =
        filmRepository.getTvShows()

    override fun getDetailTvShow(id: String, tvShowId: String): Flow<Resource<TvShow>> =
        filmRepository.getDetailTvShow(id, tvShowId)

    override fun getDetailMovie(id: String, movieId: String): Flow<Resource<Movie>> =
        filmRepository.getDetailMovie(id, movieId)

    override fun getFavoriteMovies(): Flow<List<Movie>> =
        filmRepository.getFavoriteMovies()

    override fun getFavoriteTvShows(): Flow<List<TvShow>> =
        filmRepository.getFavoriteTvShows()

    override fun getSortMovies(
        favorited: Boolean,
        sort: String
    ): Flow<List<Movie>> =
        filmRepository.getSortMovies(favorited, sort)

    override fun getSortTvShows(
        favorited: Boolean,
        sort: String
    ): Flow<List<TvShow>> =
        filmRepository.getSortTvShows(favorited, sort)

    override fun updateMovie(movie: Movie) =
        filmRepository.updateMovie(movie)

    override fun updateTvShow(tvShow: TvShow) =
        filmRepository.updateTvShow(tvShow)

    override fun getComment(filmType: String, filmId: String): Flow<List<Comment>> =
        filmRepository.getComment(filmType, filmId)

    override fun insertComment(comment: Comment) =
        filmRepository.insertComment(comment)
}