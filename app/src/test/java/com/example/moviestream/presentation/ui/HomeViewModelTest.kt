package com.example.moviestream.presentation.ui

import app.cash.turbine.test
import com.example.moviestream.core.BaseResult
import com.example.moviestream.core.domain.usecase.MovieUseCase
import com.example.moviestream.data.emptyMovieGenre
import com.example.moviestream.data.movieGenre
import com.example.moviestream.presentation.home.HomeUiState
import com.example.moviestream.presentation.home.HomeViewModel
import com.example.moviestream.utils.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.spyk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    private val movieUseCase = spyk<MovieUseCase>()
    private lateinit var sut: HomeViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)

        sut = HomeViewModel(movieUseCase = movieUseCase)
    }

    @Test
    fun `test loading list movie genre`() = runBlocking {
        coEvery {
            movieUseCase.getListGenreMovie()
        } returns flowOf()

        sut.getMovieGenre()

        sut.movieGenre.take(1).test {
            when(val result = awaitItem()) {
                is HomeUiState.Loading -> {
                    assertEquals(
                        true,
                        result.loading
                    )
                }
                else -> Unit
            }
            awaitComplete()
         }

        coVerify(exactly = 2) {
            movieUseCase.getListGenreMovie()
        }

        confirmVerified(movieUseCase)
    }

    @Test
    fun `test load list movie genre on success request data` () = runBlocking {
        coEvery {
            movieUseCase.getListGenreMovie()
        } returns flowOf(BaseResult.Success(movieGenre))

        sut.getMovieGenre()

        sut.movieGenre.take(1).test {
            when(val result = awaitItem()) {
                is HomeUiState.SuccessGenreMovie -> {
                    assertEquals(
                        movieGenre,
                        result.genre
                    )
                }
                else -> Unit
            }
            awaitComplete()
        }

        coVerify(exactly = 2) {
            movieUseCase.getListGenreMovie()
        }

        confirmVerified(movieUseCase)
    }

    @Test
    fun `test load list movie genre on success empty data`() = runBlocking {
        coEvery {
            movieUseCase.getListGenreMovie()
        } returns flowOf(BaseResult.Success(emptyMovieGenre))

        sut.getMovieGenre()

        sut.movieGenre.take(1).test {
            when(val result = awaitItem()) {
                is HomeUiState.SuccessGenreMovie -> {
                    assertEquals(
                        emptyMovieGenre,
                        result.genre
                    )
                }
                else -> Unit
            }
            awaitComplete()
        }

        coVerify(exactly = 2) {
            movieUseCase.getListGenreMovie()
        }

        confirmVerified(movieUseCase)
    }

    @After
    fun teardown() {
        clearAllMocks()
    }
}