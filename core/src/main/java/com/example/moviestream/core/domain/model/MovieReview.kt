package com.example.moviestream.core.domain.model

data class MovieReview(
    val id: Int,
    val page: Int,
    val totalPages: Int,
    val results: List<MovieReviewItem>,
    val totalResults: Int
)

data class Author(
    val avatarPath: String,
    val name: String,
    val rating: Double,
    val username: String
)

data class MovieReviewItem(
    val authorDetails: Author,
    val author: String,
    val createdAt: String,
    val id: String,
    val content: String,
)
