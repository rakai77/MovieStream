package com.example.moviestream.core.data

import com.example.moviestream.core.data.remote.MovieGenreResponse
import com.example.moviestream.core.domain.model.MovieGenre

fun MovieGenreResponse.toDomain() = MovieGenre(
    name = this.name ?: "",
    id = this.id ?: 0
)