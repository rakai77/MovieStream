package com.example.moviestream.core.domain

import app.cash.turbine.test
import com.example.moviestream.core.BaseResult
import com.example.moviestream.core.data.movieDetail
import com.example.moviestream.core.data.movieGenre
import com.example.moviestream.core.domain.interactor.MovieInteractor
import com.example.moviestream.core.domain.repository.MovieRepository
import com.example.moviestream.core.domain.usecase.MovieUseCase
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.spyk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MovieUseCaseTest {

    private val repository = spyk<MovieRepository>()
    private lateinit var sut: MovieUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        sut = MovieInteractor(repository)
    }

    @Test
    fun `test load get Movie Genre`() = runBlocking {
        coEvery {
            repository.listMovieGenre()
        } returns flowOf()

        sut.getListGenreMovie().test {
            awaitComplete()
        }

        coVerify(exactly = 1) {
            repository.listMovieGenre()
        }

        confirmVerified(repository)
    }

    @Test
    fun `test load get Movie Detail`() = runBlocking {
        coEvery {
            repository.getMovieDetail("12")
        } returns flowOf()

        sut.getMovieDetail("12").test {
            awaitComplete()
        }

        coVerify(exactly = 1) {
            repository.getMovieDetail("12")
        }

        confirmVerified(repository)
    }

    @Test
    fun `test load get movie genre with data`() = runBlocking {
        coEvery {
            repository.listMovieGenre()
        } returns flowOf(BaseResult.Success(movieGenre))

        sut.getListGenreMovie().test {
            when(val result = awaitItem()) {
                is BaseResult.Success -> {
                    assertEquals(
                        result.data,
                        movieGenre
                    )
                }
                else -> Unit
            }
            awaitComplete()
        }

        coVerify(exactly = 1) {
            repository.listMovieGenre()
        }

        confirmVerified(repository)
    }

    @Test
    fun `test load get movie detail with data`() = runBlocking {
        coEvery {
            repository.getMovieDetail("12")
        } returns flowOf(BaseResult.Success(movieDetail))

        sut.getMovieDetail("12").test {
            when(val result = awaitItem()) {
                is BaseResult.Success -> {
                    assertEquals(
                        result.data,
                        movieDetail
                    )
                }
                else -> Unit
            }
            awaitComplete()
        }

        coVerify(exactly = 1) {
            repository.getMovieDetail("12")
        }

        confirmVerified(repository)
    }

    @After
    fun teardown() { clearAllMocks() }
}