package com.dicoding.moviecatalogue.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.moviecatalogue.core.domain.usecase.FilmUseCase

class TvShowViewModel(private val filmUseCase: FilmUseCase): ViewModel() {
    fun getTvShows() = filmUseCase.getTvShows().asLiveData()

    fun getFavoriteTvShows() = filmUseCase.getFavoriteTvShows().asLiveData()

    fun getSortTvShows(favorite:Boolean, sort: String) = filmUseCase.getSortTvShows(favorite, sort).asLiveData()
}