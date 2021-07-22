package com.dicoding.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.moviecatalogue.core.data.Resource
import com.dicoding.moviecatalogue.core.domain.model.Movie
import com.dicoding.moviecatalogue.core.domain.model.TvShow
import com.dicoding.moviecatalogue.core.domain.usecase.FilmUseCase

class DetailFilmViewModel(private val filmUseCase: FilmUseCase) : ViewModel() {
    var movie = Movie()
    var tvShow = TvShow()

    fun getMovie(id: String, movieId: String): LiveData<Resource<Movie>> =
        filmUseCase.getDetailMovie(id, movieId).asLiveData()

    fun getTvShow(id:String, tvShowId: String): LiveData<Resource<TvShow>> =
        filmUseCase.getDetailTvShow(id, tvShowId).asLiveData()

    fun updateMovie(){
        filmUseCase.updateMovie(movie)
    }

    fun updateTvShow() {
        filmUseCase.updateTvShow(tvShow)
    }
}