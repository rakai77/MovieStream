package com.example.moviestream.core.domain.usecase

import androidx.paging.PagingData
import com.example.moviestream.core.BaseResult
import com.example.moviestream.core.domain.model.MovieDetail
import com.example.moviestream.core.domain.model.MovieGenre
import com.example.moviestream.core.domain.model.MovieItem
import com.example.moviestream.core.domain.model.MovieReviewItem
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    suspend fun getListGenreMovie() : Flow<BaseResult<MovieGenre>>

    fun getListMovieByGenre(genreId: String) : Flow<PagingData<MovieItem>>

    suspend fun getMovieDetail(movieId: String) : Flow<BaseResult<MovieDetail>>

    fun getListMovieReview(movieId: String) : Flow<PagingData<MovieReviewItem>>
}