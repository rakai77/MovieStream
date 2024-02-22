package com.example.moviestream.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviestream.core.BaseResult
import com.example.moviestream.core.domain.model.MovieDetail
import com.example.moviestream.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    private var _movieDetail = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Idle)
    val movieDetail = _movieDetail.asStateFlow()

    fun getMovieDetail(movieId: String) {
        viewModelScope.launch {
            movieUseCase.getMovieDetail(movieId)
                .onStart {
                    _movieDetail.value = MovieDetailUiState.Loading(true)
                }
                .catch {
                    _movieDetail.value = MovieDetailUiState.Loading(false)
                }
                .collect { result ->
                    _movieDetail.value = MovieDetailUiState.Loading(false)
                    when(result) {
                        is BaseResult.Success -> {
                            _movieDetail.value = MovieDetailUiState.Success(result.data)
                        }
                        is BaseResult.Error -> {
                            _movieDetail.value = MovieDetailUiState.Error(result.message)
                        }
                    }
                }
        }
    }
}