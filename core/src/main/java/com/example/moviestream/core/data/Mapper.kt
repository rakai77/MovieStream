package com.example.moviestream.core.data

import com.example.moviestream.core.data.remote.AuthorDetails
import com.example.moviestream.core.data.remote.GenresItemResult
import com.example.moviestream.core.data.remote.MovieByGenreResponse
import com.example.moviestream.core.data.remote.MovieDetailResponse
import com.example.moviestream.core.data.remote.MovieGenreResponse
import com.example.moviestream.core.data.remote.MovieResultsItem
import com.example.moviestream.core.data.remote.MovieReviewResponse
import com.example.moviestream.core.data.remote.MovieReviewResult
import com.example.moviestream.core.domain.model.Author
import com.example.moviestream.core.domain.model.MovieByGenre
import com.example.moviestream.core.domain.model.MovieDetail
import com.example.moviestream.core.domain.model.MovieGenre
import com.example.moviestream.core.domain.model.MovieGenreItem
import com.example.moviestream.core.domain.model.MovieItem
import com.example.moviestream.core.domain.model.MovieReview
import com.example.moviestream.core.domain.model.MovieReviewItem

fun MovieGenreResponse.toDomain() = MovieGenre(
    genres = this.genres?.map { it.toDomain() } ?: emptyList()
)

fun GenresItemResult.toDomain() = MovieGenreItem(
    name = this.name ?: "",
    id = this.id ?: 0
)

fun MovieByGenreResponse.toDomain() = MovieByGenre(
    page = this.page ?: 0,
    totalPages = this.totalPages ?:0,
    results = this.results?.map { it.toDomain() } ?: emptyList(),
    totalResults = this.totalResults ?: 0
)

fun MovieResultsItem.toDomain() = MovieItem(
    id = this.id ?: 0,
    title = this.title ?: "",
    posterPath = this.posterPath ?: ""
)

fun MovieDetailResponse.toDomain() = MovieDetail(
    id = this.id ?: 0,
    title = this.title ?: "",
    genres = this.genres?.map { it.toDomain() } ?: emptyList(),
    overview = this.overview ?: "",
    posterPath = this.posterPath ?: "",
    releaseDate = this.releaseDate ?: "",
    voteAverage = this.voteAverage ?: 0.0,
    status = this.status ?: ""
)

fun MovieReviewResponse.toDomain() = MovieReview(
    id = this.id ?: 0,
    page = this.page ?: 0,
    totalPages = this.totalPages ?:0,
    results = this.results?.map { it.toDomain() } ?: emptyList(),
    totalResults = this.totalResults ?: 0
)

fun MovieReviewResult.toDomain() = MovieReviewItem(
    authorDetails = this.authorDetails.toDomain(),
    author = this.author ?: "",
    createdAt = this.createdAt ?: "",
    id = this.id ?: "",
    content = this.content ?: ""
)

fun AuthorDetails.toDomain() = Author(
    avatarPath = this.avatarPath ?: "",
    name = this.name ?: "",
    rating = this.rating ?: 0.0,
    username = this.username ?: ""
)