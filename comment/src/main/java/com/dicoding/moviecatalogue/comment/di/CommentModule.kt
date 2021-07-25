package com.dicoding.moviecatalogue.comment.di

import com.dicoding.moviecatalogue.comment.ui.CommentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val commentModule = module {
    viewModel { CommentViewModel(get()) }
}