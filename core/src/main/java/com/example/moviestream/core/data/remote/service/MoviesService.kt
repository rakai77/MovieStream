package com.example.moviestream.core.data.remote.service

import com.example.moviestream.core.data.remote.MovieByGenreResponse
import com.example.moviestream.core.data.remote.MovieGenreResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("genre/movie/list")
    suspend fun listMovieGenre(
        @Query("api_key") query: String = "b917efbd6df2adf02c62cf3b78882e78"
    ) : Response<MovieGenreResponse>

    @GET("discover/movie")
    suspend fun listMovieByGenre(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String,
        @Query("api_key") query: String = "b917efbd6df2adf02c62cf3b78882e78",
    ) : Response<MovieByGenreResponse>
}