package com.dicoding.moviecatalogue.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.moviecatalogue.core.domain.usecase.FilmUseCase

class MovieViewModel(private val filmUseCase: FilmUseCase): ViewModel() {

    fun getMovies() = filmUseCase.getMovies().asLiveData()

    fun getFavoriteMovies() = filmUseCase.getFavoriteMovies().asLiveData()

    fun getSortMovies(favorite:Boolean, sort: String) = filmUseCase.getSortMovies(favorite, sort).asLiveData()
}