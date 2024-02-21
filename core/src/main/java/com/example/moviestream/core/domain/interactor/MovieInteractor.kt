package com.example.moviestream.core.domain.interactor

import com.example.moviestream.core.BaseResult
import com.example.moviestream.core.domain.model.MovieGenre
import com.example.moviestream.core.domain.repository.MovieRepository
import com.example.moviestream.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val movieRepository: MovieRepository
) : MovieUseCase{
    override suspend fun getListGenreMovie(): Flow<BaseResult<MovieGenre>> {
        return movieRepository.listMovieGenre()
    }
}