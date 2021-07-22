package com.dicoding.moviecatalogue.favorite.di

import com.dicoding.moviecatalogue.favorite.movie.MovieViewModel
import com.dicoding.moviecatalogue.favorite.tvshow.TvShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TvShowViewModel(get()) }
}