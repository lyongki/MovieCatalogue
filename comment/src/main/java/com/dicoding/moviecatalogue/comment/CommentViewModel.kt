package com.dicoding.moviecatalogue.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.moviecatalogue.core.domain.model.Comment
import com.dicoding.moviecatalogue.core.domain.usecase.FilmUseCase

class CommentViewModel(private val filmUseCase: FilmUseCase): ViewModel() {
    var filmId: String = ""
    var filmType: String = ""

    fun getComment(): LiveData<List<Comment>> =
        filmUseCase.getComment(filmType, filmId).asLiveData()

    fun insertComment(comment: Comment){
        filmUseCase.insertComment(comment)
    }
}