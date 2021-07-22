package com.dicoding.moviecatalogue.core.domain.model

class Movie (
    val id: Int = 0,
    var movieId: String = "",
    var title: String = "",
    var year: String = "",
    var genre: String = "",
    var productionCompanies: String = "",
    var overview: String = "",
    var duration: String = "",
    var rating: String = "",
    var imagePath: String = "",
    var favorited: Boolean = false
)