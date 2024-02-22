package com.example.moviestream.core.domain.model

data class MovieByGenre(
    val page: Int,
    val totalPages: Int,
    val results: List<MovieItem>,
    val totalResults: Int
)

data class MovieItem(
    val id: Int,
    val title: String,
    val posterPath: String
)
