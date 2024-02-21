package com.example.moviestream.core.domain.model

data class MovieGenre(
    val genres: List<MovieGenreItem>
)
data class MovieGenreItem(
    val name: String,
    val id: Int
)