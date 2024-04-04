package com.example.moviestream.core.data

import com.example.moviestream.core.data.remote.MovieDetailResponse
import com.example.moviestream.core.domain.model.MovieDetail
import com.example.moviestream.core.domain.model.MovieGenreItem

val listGenre = listOf(
    MovieGenreItem(
        name = "Horror",
        id = 1
    ),
    MovieGenreItem(
        name = "Comedy",
        id = 2
    ),
    MovieGenreItem(
        name = "Action",
        id = 3
    ),
    MovieGenreItem(
        name = "Drama",
        id = 4
    ),
    MovieGenreItem(
        name = "Thriller",
        id = 5
    )
)

val movieDetail = MovieDetail(
    id = 1,
    title = "Iron Man",
    genres = listOf(),
    overview = "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum",
    posterPath = "www.tmdb.com",
    releaseDate = "25 Jan 2008",
    voteAverage = 8.7,
    status = "release"
)