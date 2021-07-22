package com.dicoding.moviecatalogue.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailTvShowResponse(

	@field:SerializedName("first_air_date")
	val firstAirDate: String,

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("production_companies")
	val productionCompanies: List<ProductionCompaniesItem>,

	@field:SerializedName("genres")
	val genres: List<GenresItem>,

	@field:SerializedName("original_name")
	val originalName: String,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("episode_run_time")
	val episodeRunTime: List<Int>,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("poster_path")
	val posterPath: String
)
