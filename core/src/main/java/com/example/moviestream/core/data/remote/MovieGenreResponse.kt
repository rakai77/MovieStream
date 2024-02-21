package com.example.moviestream.core.data.remote

import com.google.gson.annotations.SerializedName

data class MovieGenreResponse(

	@field:SerializedName("genres")
	val genres: List<GenresItemResult>? = null
)

data class GenresItemResult(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)