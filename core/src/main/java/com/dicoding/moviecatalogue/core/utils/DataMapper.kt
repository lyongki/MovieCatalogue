package com.dicoding.moviecatalogue.core.utils

import com.dicoding.moviecatalogue.core.data.source.local.entity.CommentEntity
import com.dicoding.moviecatalogue.core.data.source.local.entity.MovieEntity
import com.dicoding.moviecatalogue.core.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.core.domain.model.Comment
import com.dicoding.moviecatalogue.core.domain.model.Movie
import com.dicoding.moviecatalogue.core.domain.model.TvShow

object DataMapper {
    fun mapMovieEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            with(it) {
                Movie(
                    id,
                    movieId,
                    title,
                    year,
                    genre,
                    productionCompanies,
                    overview,
                    duration,
                    rating,
                    imagePath,
                    favorited
                )
            }
        }

    fun mapTvShowEntitiesToDomain(input: List<TvShowEntity>): List<TvShow> =
        input.map {
            with(it) {
                TvShow(
                    id,
                    tvShowId,
                    title,
                    year,
                    genre,
                    productionCompanies,
                    overview,
                    duration,
                    rating,
                    imagePath,
                    favorited
                )
            }
        }

    fun mapMovieDomainToEntity(movie: Movie): MovieEntity =
        with(movie) {
            MovieEntity(
                id,
                movieId,
                title,
                year,
                genre,
                productionCompanies,
                overview,
                duration,
                rating,
                imagePath,
                favorited
            )
        }

    fun mapTvShowDomainToEntity(tvShow: TvShow): TvShowEntity =
        with(tvShow) {
            TvShowEntity(
                id,
                tvShowId,
                title,
                year,
                genre,
                productionCompanies,
                overview,
                duration,
                rating,
                imagePath,
                favorited
            )
        }

    fun mapTvShowEntityToDomain(tvShowEntity: TvShowEntity): TvShow =
        with(tvShowEntity) {
            TvShow(
                id,
                tvShowId,
                title,
                year,
                genre,
                productionCompanies,
                overview,
                duration,
                rating,
                imagePath,
                favorited
            )
        }

    fun mapMovieEntityToDomain(movieEntity: MovieEntity): Movie =
        with(movieEntity) {
            Movie(
                id,
                movieId,
                title,
                year,
                genre,
                productionCompanies,
                overview,
                duration,
                rating,
                imagePath,
                favorited
            )
        }

    fun mapCommentEntitiesToDomain(input: List<CommentEntity>): List<Comment> =
        input.map {
            with(it) {
                Comment(
                    id,
                    film_type,
                    film_id,
                    user_id,
                    date,
                    comment
                )
            }
        }

    fun mapCommentDomainToEntity(comment: Comment): CommentEntity =
        with(comment) {
            CommentEntity(
                id,
                film_type,
                film_id,
                user_id,
                date,
                this.comment
            )
        }
}