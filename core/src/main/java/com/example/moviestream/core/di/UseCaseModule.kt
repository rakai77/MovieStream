package com.example.moviestream.core.di

import com.example.moviestream.core.domain.interactor.MovieInteractor
import com.example.moviestream.core.domain.usecase.MovieUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindMovieUseCase(movieInteractor: MovieInteractor) : MovieUseCase
}