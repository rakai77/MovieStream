package com.example.moviestream.core.domain.interactor

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviestream.core.BaseResult
import com.example.moviestream.core.domain.model.MovieGenre
import com.example.moviestream.core.domain.model.MovieItem
import com.example.moviestream.core.domain.repository.MovieRepository
import com.example.moviestream.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val movieRepository: MovieRepository
) : MovieUseCase {
    override suspend fun getListGenreMovie(): Flow<BaseResult<MovieGenre>> {
        return movieRepository.listMovieGenre()
    }

    override fun getListMovieByGenre(genreId: String): Flow<PagingData<MovieItem>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = 20,
                initialLoadSize = 20
            ),
            pagingSourceFactory = { movieRepository.listMovieByGenre(genreId) }
        ).flow
    }
}