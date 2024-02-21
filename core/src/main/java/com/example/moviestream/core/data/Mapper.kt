package com.example.moviestream.core.data

import com.example.moviestream.core.data.remote.GenresItemResult
import com.example.moviestream.core.data.remote.MovieGenreResponse
import com.example.moviestream.core.domain.model.MovieGenre
import com.example.moviestream.core.domain.model.MovieGenreItem

fun MovieGenreResponse.toDomain() = MovieGenre(
    genres = this.genres?.map { it.toDomain() } ?: emptyList()
)

fun GenresItemResult.toDomain() = MovieGenreItem(
    name = this.name ?: "",
    id = this.id ?: 0
)