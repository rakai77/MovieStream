package com.example.moviestream.core.data.remote

import com.google.gson.annotations.SerializedName

data class MovieByGenreResponse(

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	val results: List<MovieResultsItem>? = null,

	@field:SerializedName("total_results")
	val totalResults: Int? = null
)

data class MovieResultsItem(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("poster_path")
	val posterPath: String? = null
)
