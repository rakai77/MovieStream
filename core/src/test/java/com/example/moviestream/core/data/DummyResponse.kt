package com.example.moviestream.core.data

import com.example.moviestream.core.domain.model.Author
import com.example.moviestream.core.domain.model.MovieDetail
import com.example.moviestream.core.domain.model.MovieGenre
import com.example.moviestream.core.domain.model.MovieGenreItem
import com.example.moviestream.core.domain.model.MovieItem
import com.example.moviestream.core.domain.model.MovieReviewItem

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

val movieGenre = MovieGenre(
    genres = listGenre
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

val movieItem = listOf(
    MovieItem(
        id = 1,
        title = "Lorem ipsum",
        posterPath = "%%%%%%"
    ),
    MovieItem(
        id = 2,
        title = "Lorem ipsum",
        posterPath = "%%%%%%"
    ),
    MovieItem(
        id = 3,
        title = "Lorem ipsum",
        posterPath = "%%%%%%"
    )
)

val authorDetails = Author(
    avatarPath = "Lorem ipsum",
    name = "Lorem ipsum",
    rating = 0.0,
    username = "lorem ipsum"
)

val movieReviewItem = listOf(
    MovieReviewItem(
        authorDetails = authorDetails,
        author = "Lorem ipsum",
        createdAt = "Lorem ipsum",
        id = "12",
        content = "Lorem ipsum"
    ),
    MovieReviewItem(
        authorDetails = authorDetails,
        author = "Lorem ipsum",
        createdAt = "Lorem ipsum",
        id = "12",
        content = "Lorem ipsum"
    ),
    MovieReviewItem(
        authorDetails = authorDetails,
        author = "Lorem ipsum",
        createdAt = "Lorem ipsum",
        id = "12",
        content = "Lorem ipsum"
    )
)