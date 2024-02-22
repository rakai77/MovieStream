package com.example.moviestream.presentation.detail

import com.example.moviestream.core.domain.model.MovieDetail

sealed class MovieDetailUiState {
    data class Success(val movieDetail: MovieDetail) : MovieDetailUiState()
    data class Error(val message: String) : MovieDetailUiState()
    data class Loading(val loading: Boolean) : MovieDetailUiState()
    data object Idle : MovieDetailUiState()
}