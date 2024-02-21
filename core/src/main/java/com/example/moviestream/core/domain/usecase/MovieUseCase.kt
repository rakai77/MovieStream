package com.example.moviestream.core.domain.usecase

import com.example.moviestream.core.BaseResult
import com.example.moviestream.core.domain.model.MovieGenre
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    suspend fun getListGenreMovie() : Flow<BaseResult<MovieGenre>>
}