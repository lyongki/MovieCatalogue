package com.dicoding.moviecatalogue.core.data

import com.dicoding.moviecatalogue.core.data.source.local.LocalDataSource
import com.dicoding.moviecatalogue.core.data.source.local.entity.MovieEntity
import com.dicoding.moviecatalogue.core.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.core.data.source.remote.RemoteDataSource
import com.dicoding.moviecatalogue.core.data.source.remote.api.ApiResponse
import com.dicoding.moviecatalogue.core.data.source.remote.response.DetailMovieResponse
import com.dicoding.moviecatalogue.core.data.source.remote.response.DetailTvShowResponse
import com.dicoding.moviecatalogue.core.data.source.remote.response.MoviesResponse
import com.dicoding.moviecatalogue.core.data.source.remote.response.TvShowsResponse
import com.dicoding.moviecatalogue.core.domain.model.Comment
import com.dicoding.moviecatalogue.core.domain.model.Movie
import com.dicoding.moviecatalogue.core.domain.model.TvShow
import com.dicoding.moviecatalogue.core.domain.repository.IFilmRepository
import com.dicoding.moviecatalogue.core.utils.AppExecutors
import com.dicoding.moviecatalogue.core.utils.DataMapper
import com.dicoding.moviecatalogue.core.utils.SortUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FilmRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    IFilmRepository {
    override fun getMovies(): Flow<Resource<List<Movie>>> {
        return object :
            com.dicoding.moviecatalogue.core.data.NetworkBoundResource<List<Movie>, MoviesResponse>() {
            override suspend fun saveCallResult(data: MoviesResponse?) {
                val movieList = ArrayList<MovieEntity>()
                for (movie in (data as MoviesResponse).results) {
                    movieList.add(
                        MovieEntity(
                            movieId = movie.id.toString(),
                            title = movie.originalTitle,
                            year = movie.releaseDate.substring(0, 4),
                            overview = movie.overview,
                            rating = movie.voteAverage.toString(),
                            imagePath = "${RemoteDataSource.IMAGE_BASE_URL}${movie.posterPath}",
                            favorited = false,
                        )
                    )
                }
                movieList.sortWith { o1, o2 ->
                    Integer.parseInt(o1?.year.toString())
                        .compareTo(Integer.parseInt(o2?.year.toString()))
                }
                localDataSource.insertMovies(movieList)
            }

            override suspend fun createCall(): Flow<ApiResponse<MoviesResponse>> =
                remoteDataSource.getMovies()

            override fun shouldFetch(data: List<Movie>): Boolean = data.isEmpty()

            override fun loadFromDB(): Flow<List<Movie>> =
                localDataSource.getMovies().map { DataMapper.mapMovieEntitiesToDomain(it) }

        }.asFlow()
    }

    override fun getTvShows(): Flow<Resource<List<TvShow>>> {
        return object :
            com.dicoding.moviecatalogue.core.data.NetworkBoundResource<List<TvShow>, TvShowsResponse>() {
            override suspend fun saveCallResult(data: TvShowsResponse?) {
                val tvShowList = ArrayList<TvShowEntity>()
                for (tvShow in (data as TvShowsResponse).results) {
                    tvShowList.add(
                        TvShowEntity(
                            tvShowId = tvShow.id.toString(),
                            title = tvShow.originalName,
                            year = tvShow.firstAirDate.substring(0, 4),
                            overview = tvShow.overview,
                            rating = tvShow.voteAverage.toString(),
                            imagePath = "${RemoteDataSource.IMAGE_BASE_URL}${tvShow.posterPath}",
                            favorited = false
                        )
                    )
                }

                tvShowList.sortWith{ o1, o2 ->
                    Integer.parseInt(o1?.year.toString())
                        .compareTo(Integer.parseInt(o2?.year.toString()))
                }

                localDataSource.insertTvShows(tvShowList)
            }

            override suspend fun createCall(): Flow<ApiResponse<TvShowsResponse>> =
                remoteDataSource.getTvShows()

            override fun shouldFetch(data: List<TvShow>): Boolean = data.isEmpty()

            override fun loadFromDB(): Flow<List<TvShow>> =
                localDataSource.getTvShows().map { DataMapper.mapTvShowEntitiesToDomain(it) }

        }.asFlow()
    }

    override fun getDetailTvShow(id:String, tvShowId: String): Flow<Resource<TvShow>> {
        return object : com.dicoding.moviecatalogue.core.data.NetworkBoundResource<TvShow, DetailTvShowResponse>() {
            override suspend fun saveCallResult(data: DetailTvShowResponse?) {
                with(data as DetailTvShowResponse) {
                    val genre = StringBuilder()
                    for (item in genres) {
                        genre.append(item.name)
                        if (item != genres.last()) genre.append(", ")
                    }

                    val productionCompanion = StringBuilder()
                    for (item in productionCompanies) {
                        productionCompanion.append(item.name)
                        if (item != productionCompanies.last()) productionCompanion.append(", ")
                    }

                    val filmEntity = TvShowEntity(
                        Integer.parseInt(id),
                        this.id.toString(),
                        originalName,
                        firstAirDate.substring(0, 4),
                        genre.toString(),
                        productionCompanion.toString(),
                        overview,
                        episodeRunTime[0].toString(),
                        voteAverage.toString(),
                        "${RemoteDataSource.IMAGE_BASE_URL}${posterPath}",
                        false
                    )
                    localDataSource.insertTvShows(listOf(filmEntity))
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<DetailTvShowResponse>> =
                remoteDataSource.getDetailTvShow(tvShowId)

            override fun shouldFetch(data: TvShow): Boolean {
                return data.genre == "" && data.productionCompanies == "" && data.duration == ""
            }

            override fun loadFromDB(): Flow<TvShow> = localDataSource.getTvShow(id).map {
                DataMapper.mapTvShowEntityToDomain(it)
            }

        }.asFlow()
    }

    override fun getDetailMovie(id: String, movieId: String): Flow<Resource<Movie>> {
        return object : com.dicoding.moviecatalogue.core.data.NetworkBoundResource<Movie, DetailMovieResponse>() {
            override suspend fun saveCallResult(data: DetailMovieResponse?) {
                with(data as DetailMovieResponse) {
                    val genre = StringBuilder()
                    for (item in genres) {
                        genre.append(item.name)
                        if (item != genres.last()) genre.append(", ")
                    }

                    val productionCompanion = StringBuilder()
                    for (item in productionCompanies) {
                        productionCompanion.append(item.name)
                        if (item != productionCompanies.last()) productionCompanion.append(", ")
                    }

                    val filmEntity = MovieEntity(
                        Integer.parseInt(id),
                        this.id.toString(),
                        originalTitle,
                        releaseDate.substring(0, 4),
                        genre.toString(),
                        productionCompanion.toString(),
                        overview,
                        runtime.toString(),
                        voteAverage.toString(),
                        "${RemoteDataSource.IMAGE_BASE_URL}${posterPath}",
                        false,
                    )
                    localDataSource.insertMovies(listOf(filmEntity))
                }

            }

            override suspend fun createCall(): Flow<ApiResponse<DetailMovieResponse>> =
                remoteDataSource.getDetailMovie(movieId)


            override fun shouldFetch(data: Movie): Boolean =
                data.genre == "" && data.productionCompanies == "" && data.duration == ""

            override fun loadFromDB(): Flow<Movie> = localDataSource.getMovie(id).map {
                DataMapper.mapMovieEntityToDomain(it)
            }

        }.asFlow()
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> =
        localDataSource.getFavoriteMovies().map {
            DataMapper.mapMovieEntitiesToDomain(it)
        }

    override fun getFavoriteTvShows(): Flow<List<TvShow>> =
        localDataSource.getFavoriteTvShows().map {
            DataMapper.mapTvShowEntitiesToDomain(it)
        }

    override fun getSortMovies(
        favorited: Boolean,
        sort: String
    ): Flow<List<Movie>> =
        localDataSource.getSortMovies(
            SortUtils.getSortedQuery(
                "movieentities",
                favorited,
                sort
            )
        ).map {
            DataMapper.mapMovieEntitiesToDomain(it)
        }

    override fun getSortTvShows(
        favorited: Boolean,
        sort: String
    ): Flow<List<TvShow>> =
        localDataSource.getSortTvShows(
            SortUtils.getSortedQuery(
                "tvshowentities",
                favorited,
                sort
            )
        ).map { DataMapper.mapTvShowEntitiesToDomain(it) }

    override fun updateMovie(movie: Movie) {
        val movieEntity = DataMapper.mapMovieDomainToEntity(movie)
        appExecutors.diskIo().execute {
            localDataSource.updateMovie(movieEntity)
        }
    }

    override fun updateTvShow(tvShow: TvShow) {
        val tvShowEntity = DataMapper.mapTvShowDomainToEntity(tvShow)
        appExecutors.diskIo().execute {
            localDataSource.updateTvShow(tvShowEntity)
        }
    }

    override fun getComment(filmType: String, filmId: String): Flow<List<Comment>> =
        localDataSource.getComment(filmType, filmId).map {
            DataMapper.mapCommentEntitiesToDomain(it)
        }

    override fun insertComment(comment: Comment) {
        val commentEntity = DataMapper.mapCommentDomainToEntity(comment)
        appExecutors.diskIo().execute{
            localDataSource.insertComment(commentEntity)
        }
    }
}