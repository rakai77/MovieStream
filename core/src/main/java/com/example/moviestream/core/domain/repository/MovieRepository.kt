package com.example.moviestream.core.domain.repository

import com.example.moviestream.core.BaseResult
import com.example.moviestream.core.domain.model.MovieGenre
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun listMovieGenre() : Flow<BaseResult<MovieGenre>>
}