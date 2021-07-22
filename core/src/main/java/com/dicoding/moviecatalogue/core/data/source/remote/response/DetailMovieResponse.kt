package com.dicoding.moviecatalogue.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailMovieResponse(

	@field:SerializedName("genres")
	val genres: List<GenresItem>,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("original_title")
	val originalTitle: String,

	@field:SerializedName("runtime")
	val runtime: Int,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("production_companies")
	val productionCompanies: List<ProductionCompaniesItem>,

	@field:SerializedName("release_date")
	val releaseDate: String,

	@field:SerializedName("vote_average")
	val voteAverage: Double
)

data class ProductionCompaniesItem(

	@field:SerializedName("name")
	val name: String
)

data class GenresItem(

	@field:SerializedName("name")
	val name: String
)