package com.dicoding.moviecatalogue.comment

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val commentModule = module {
    viewModel { CommentViewModel(get()) }
}