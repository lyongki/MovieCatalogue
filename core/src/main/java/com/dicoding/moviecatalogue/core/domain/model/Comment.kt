package com.dicoding.moviecatalogue.core.domain.model

data class Comment (
    val id: Int = 0,
    val film_type: String = "",
    val film_id: String = "",
    val user_id: String = "",
    val date: String = "",
    val comment: String = ""
)