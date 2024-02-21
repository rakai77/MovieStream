package com.example.moviestream.core.di

import com.example.moviestream.core.data.source.MovieRemoteDataSource
import com.example.moviestream.core.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMovieRepository(movieRemoteDataSource: MovieRemoteDataSource) : MovieRepository
}