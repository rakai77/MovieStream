package com.example.moviestream.core.repository

import app.cash.turbine.test
import com.example.moviestream.core.BaseResult
import com.example.moviestream.core.data.listGenre
import com.example.moviestream.core.data.remote.GenresItemResult
import com.example.moviestream.core.data.remote.MovieGenreResponse
import com.example.moviestream.core.data.remote.service.MoviesService
import com.example.moviestream.core.data.source.MovieRemoteDataSource
import com.example.moviestream.core.domain.repository.MovieRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MovieRepositoryTest {

    private val service = mockk<MoviesService>()
    private lateinit var sut: MovieRepository

    @Before
    fun setup() { sut = MovieRemoteDataSource(moviesService = service) }

    @Test
    fun `test get list movie genre on Failure 400`()  {
        expectListGenre(
            withStatusCode = 400,
            sut = sut,
            expectedResult = 400
        )
    }

    @Test
    fun `test get list movie genre on Failure 401`() {
        expectListGenre(
            withStatusCode = 401,
            sut = sut,
            expectedResult = 401
        )
    }

    @Test
    fun `test get list movie genre on Failure 404`() {
        expectListGenre(
            withStatusCode = 404,
            sut = sut,
            expectedResult = 404
        )
    }

    @Test
    fun `test get list movie genre on Failure 422`() {
        expectListGenre(
            withStatusCode = 422,
            sut = sut,
            expectedResult = 422
        )
    }

    @Test
    fun `test get list movie genre on Failure 500`() {
        expectListGenre(
            withStatusCode = 500,
            sut = sut,
            expectedResult = 500
        )
    }

    @Test
    fun `test get list movie genre on Failure Timeout`()  {
        expectListGenre(
            withStatusCode = null,
            sut = sut,
            typeException = SocketTimeoutException(),
            expectedResult = "Timeout"
        )
    }

    @Test
    fun `test get list movie genre on Failure Connectivity`()  {
        expectListGenre(
            withStatusCode = null,
            sut = sut,
            typeException = UnknownHostException(),
            expectedResult = "Check your internet connection"
        )
    }

    @Test
    fun `test get list movie genre on Unknown Error`()  {
        expectListGenre(
            withStatusCode = null,
            sut = sut,
            expectedResult = "Something went wrong"
        )
    }

    @Test
    fun `test get list movie genre on Success 200`() = runBlocking {
        val response = Response.success(MovieGenreResponse(
            genres = listOf(
                GenresItemResult(
                    name = "Horror",
                    id = 1
                ),
                GenresItemResult(
                    name = "Comedy",
                    id = 2
                ),
                GenresItemResult(
                    name = "Action",
                    id = 3
                ),
                GenresItemResult(
                    name = "Drama",
                    id = 4
                ),
                GenresItemResult(
                    name = "Thriller",
                    id = 5
                )
            )
        ))

        coEvery {
            service.listMovieGenre()
        } returns response

        sut.listMovieGenre().test {
           when(val result = awaitItem()) {
               is BaseResult.Success -> {
                   assertEquals(
                       result.data.genres,
                       listGenre
                   )
               }
               else -> Unit
           }
            awaitComplete()
        }

        coVerify(exactly = 1) {
            service.listMovieGenre()
        }

        confirmVerified(service)
    }

    @Test
    fun `test get movie detail on Failure 400`() {
        expectDetailMovie(
            withStatusCode = 400,
            sut = sut,
            expectedResult = 400
        )
    }

    @Test
    fun `test get movie detail on Failure 401`() {
        expectDetailMovie(
            withStatusCode = 401,
            sut = sut,
            expectedResult = 401
        )
    }

    @Test
    fun `test get movie detail on Failure 404`() {
        expectDetailMovie(
            withStatusCode = 404,
            sut = sut,
            expectedResult = 404
        )
    }

    @Test
    fun `test get movie detail on Failure 422`() {
        expectDetailMovie(
            withStatusCode = 422,
            sut = sut,
            expectedResult = 422
        )
    }

    @Test
    fun `test get movie detail on Failure 500`() {
        expectDetailMovie(
            withStatusCode = 500,
            sut = sut,
            expectedResult = 500
        )
    }

    @Test
    fun `test get movie detail on Failure Timeout`() {
        expectDetailMovie(
            withStatusCode = null,
            sut = sut,
            typeException = SocketTimeoutException(),
            expectedResult = "Timeout"
        )
    }

    @Test
    fun `test get movie detail on Failure Connectivity`() {
        expectDetailMovie(
            withStatusCode = null,
            sut = sut,
            typeException = UnknownHostException(),
            expectedResult = "Check your internet connection"
        )
    }

    @Test
    fun `test get movie detail on Failure Unknown Error`() {
        expectDetailMovie(
            withStatusCode = null,
            sut = sut,
            expectedResult = "Something went wrong"
        )
    }

    @After
    fun teardown() { clearAllMocks() }

    private fun expectListGenre(
        withStatusCode: Int? = null,
        sut: MovieRepository,
        typeException: Any? = null,
        expectedResult: Any
    ) = runBlocking {
        when {
            withStatusCode != null -> {
                val response = Response.error<MoviesService>(
                    withStatusCode,
                    "".toResponseBody(null)
                )
                coEvery {
                    service.listMovieGenre()
                } throws HttpException(response)
            }
            typeException is SocketTimeoutException -> {
                coEvery {
                    service.listMovieGenre()
                } throws SocketTimeoutException()
            }
            typeException is UnknownHostException -> {
                coEvery {
                    service.listMovieGenre()
                } throws UnknownHostException()
            }
            else -> {
                coEvery {
                    service.listMovieGenre()
                } throws IOException()
            }
        }

        sut.listMovieGenre().test {
            when(val result = awaitItem()) {
                is BaseResult.Error -> {
                   if (result.statusCode != null) {
                       assertEquals(
                           expectedResult,
                           result.statusCode,
                       )
                   } else {
                       assertEquals(
                           expectedResult,
                           result.message,
                       )
                   }
                }
                else -> Unit
            }
            awaitComplete()
        }

        coVerify(exactly = 1) {
            service.listMovieGenre()
        }

        confirmVerified(service)
    }

    private fun expectDetailMovie(
        withStatusCode: Int? = null,
        sut: MovieRepository,
        typeException: Any? = null,
        expectedResult: Any
    ) = runBlocking {
        when {
            withStatusCode != null -> {
                val response = Response.error<MoviesService>(
                    withStatusCode,
                    "".toResponseBody(null)
                )
                coEvery {
                    service.movieDetail("1")
                } throws HttpException(response)
            }
            typeException is SocketTimeoutException -> {
                coEvery {
                    service.movieDetail("1")
                } throws SocketTimeoutException()
            }
            typeException is UnknownHostException -> {
                coEvery {
                    service.movieDetail("1")
                } throws UnknownHostException()
            }
            else -> {
                coEvery {
                    service.movieDetail("1")
                } throws IOException()
            }
        }

        sut.getMovieDetail("1").test {
            when(val result = awaitItem()) {
                is BaseResult.Error -> {
                    if (result.statusCode != null) {
                        assertEquals(
                            expectedResult,
                            result.statusCode,
                        )
                    } else {
                        assertEquals(
                            expectedResult,
                            result.message,
                        )
                    }
                }
                else -> Unit
            }
            awaitComplete()
        }

        coVerify(exactly = 1) {
            service.movieDetail("1")
        }

        confirmVerified(service)
    }
}