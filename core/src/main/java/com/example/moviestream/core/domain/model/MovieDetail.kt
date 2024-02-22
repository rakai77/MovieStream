package com.example.moviestream.core.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val genres: List<MovieGenreItem>,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val status: String
)
