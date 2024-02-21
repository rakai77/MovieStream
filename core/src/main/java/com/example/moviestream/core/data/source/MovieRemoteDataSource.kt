package com.example.moviestream.core.data.source

import com.example.moviestream.core.BaseResult
import com.example.moviestream.core.data.remote.service.MoviesService
import com.example.moviestream.core.data.toDomain
import com.example.moviestream.core.domain.model.MovieGenre
import com.example.moviestream.core.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val moviesService: MoviesService
) : MovieRepository {
    override suspend fun listMovieGenre(): Flow<BaseResult<MovieGenre>> {
        return flow {
            try {
                val response = moviesService.listMovieGenre()
                if (response.isSuccessful) {
                    val body = response.body()!!
                    emit(BaseResult.Success(body.toDomain()))
                }
            } catch (t: Throwable) {
                when (t) {
                    is HttpException -> {
                        emit(BaseResult.Error(t.code(), t.message() ?: "something went wrong"))
                    }
                    is UnknownHostException -> {
                        emit(BaseResult.Error(null, t.message ?: "check your internet connection"))
                    }
                    is SocketTimeoutException -> {
                        emit(BaseResult.Error(null, t.message ?: "Timeout"))
                    }
                    else -> emit(BaseResult.Error(null, t.message ?: "something went wrong"))
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}