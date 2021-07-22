package com.dicoding.moviecatalogue.di

import com.dicoding.moviecatalogue.core.domain.usecase.FilmInteractor
import com.dicoding.moviecatalogue.core.domain.usecase.FilmUseCase
import com.dicoding.moviecatalogue.viewmodel.DetailFilmViewModel
import com.dicoding.moviecatalogue.viewmodel.MovieViewModel
import com.dicoding.moviecatalogue.viewmodel.TvShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory <FilmUseCase>{ FilmInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TvShowViewModel(get()) }
    viewModel { DetailFilmViewModel(get()) }
}