package com.example.moviestream.presentation.home

import com.example.moviestream.core.domain.model.MovieGenre

sealed class HomeUiState {
    data class SuccessGenreMovie(val genre: MovieGenre) : HomeUiState()
    data class Loading(val loading: Boolean) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
    data object Idle : HomeUiState()
}