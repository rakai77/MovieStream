package com.example.moviestream.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviestream.core.BaseResult
import com.example.moviestream.core.domain.model.MovieItem
import com.example.moviestream.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
): ViewModel() {

    private val _movieGenre = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val movieGenre = _movieGenre.asStateFlow()

    init {
        getMovieGenre()
    }

    fun getMovieGenre() {
        viewModelScope.launch {
            movieUseCase.getListGenreMovie()
                .onStart {
                    _movieGenre.value = HomeUiState.Loading(true)
                }
                .catch {
                    _movieGenre.value = HomeUiState.Loading(false)
                }
                .collect { result ->
                    _movieGenre.value = HomeUiState.Loading(false)
                    when(result) {
                        is BaseResult.Success -> {
                            _movieGenre.value = HomeUiState.SuccessGenreMovie(result.data)
                        }
                        is BaseResult.Error -> {
                            _movieGenre.value = HomeUiState.Error(result.message)
                        }
                    }
                }
        }
    }

    fun getMovieByGenre(genreId: String) : Flow<PagingData<MovieItem>> {
        return movieUseCase.getListMovieByGenre(genreId).cachedIn(viewModelScope)
    }
}