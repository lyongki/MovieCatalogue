package com.dicoding.moviecatalogue.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
	@field:SerializedName("results")
	val results: List<Movie>,
)

data class Movie(

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("original_title")
	val originalTitle: String,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("release_date")
	val releaseDate: String,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("id")
	val id: Int,
)
