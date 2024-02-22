package com.example.moviestream.core.domain.repository

import androidx.paging.PagingSource
import com.example.moviestream.core.BaseResult
import com.example.moviestream.core.domain.model.MovieDetail
import com.example.moviestream.core.domain.model.MovieGenre
import com.example.moviestream.core.domain.model.MovieItem
import com.example.moviestream.core.domain.model.MovieReviewItem
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun listMovieGenre() : Flow<BaseResult<MovieGenre>>

    fun listMovieByGenre(genreId: String) : PagingSource<Int, MovieItem>

    suspend fun getMovieDetail(movieId: String) : Flow<BaseResult<MovieDetail>>

    fun listMovieReview(movieId: String) : PagingSource<Int, MovieReviewItem>
}