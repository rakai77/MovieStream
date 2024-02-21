package com.example.moviestream.core.data.remote

import com.google.gson.annotations.SerializedName

data class MovieGenreResponse(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)