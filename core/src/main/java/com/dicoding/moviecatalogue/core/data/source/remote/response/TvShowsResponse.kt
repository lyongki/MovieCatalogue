package com.dicoding.moviecatalogue.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowsResponse(

	@field:SerializedName("results")
	val results: List<TVShow>,
)

data class TVShow(

	@field:SerializedName("first_air_date")
	val firstAirDate: String,

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("original_name")
	val originalName: String,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("id")
	val id: Int,
)
