package com.example.moviestream.core.repository

import androidx.paging.PagingSource
import app.cash.turbine.test
import com.example.moviestream.core.BaseResult
import com.example.moviestream.core.data.listGenre
import com.example.moviestream.core.data.movieDetail
import com.example.moviestream.core.data.movieItem
import com.example.moviestream.core.data.movieReviewItem
import com.example.moviestream.core.data.remote.AuthorDetails
import com.example.moviestream.core.data.remote.GenresItemResult
import com.example.moviestream.core.data.remote.MovieByGenreResponse
import com.example.moviestream.core.data.remote.MovieDetailResponse
import com.example.moviestream.core.data.remote.MovieGenreResponse
import com.example.moviestream.core.data.remote.MovieResultsItem
import com.example.moviestream.core.data.remote.MovieReviewResponse
import com.example.moviestream.core.data.remote.MovieReviewResult
import com.example.moviestream.core.data.remote.service.MoviesService
import com.example.moviestream.core.data.source.MovieRemoteDataSource
import com.example.moviestream.core.domain.model.MovieItem
import com.example.moviestream.core.domain.model.MovieReviewItem
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
    fun `test get MovieByGenre on Failure Timeout`() = runBlocking {
        val expectedResult = PagingSource.LoadResult.Error<Int, MovieItem>(SocketTimeoutException())

        coEvery {
            service.listMovieByGenre(1,"1")
        } throws expectedResult.throwable

        val result = sut.listMovieByGenre("1").load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        assertEquals(expectedResult, result)

        coVerify(exactly = 1) {
            service.listMovieByGenre(1, "1")
        }

        confirmVerified(service)
    }

    @Test
    fun `test get MovieByGenre on Failure Connectivity`() = runBlocking {
        val expectedResult = PagingSource.LoadResult.Error<Int, MovieItem>(IOException())

        coEvery {
            service.listMovieByGenre(1,"1")
        } throws expectedResult.throwable

        val result = sut.listMovieByGenre("1").load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        assertEquals(expectedResult, result)

        coVerify(exactly = 1) {
            service.listMovieByGenre(1, "1")
        }

        confirmVerified(service)
    }

    @Test
    fun `test get MovieByGenre on Failure HttpException`() = runBlocking {

        val exception = HttpException(Response.error<MoviesService>(404, "".toResponseBody()))
        val expectedResult = PagingSource.LoadResult.Error<Int, MovieItem>(exception)

        coEvery { service.listMovieByGenre(1,"1") } throws expectedResult.throwable

        val result = sut.listMovieByGenre("1").load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        assertEquals(expectedResult, result)

        coVerify {
            service.listMovieByGenre(1, "1")
        }

        confirmVerified(service)
    }

    @Test
    fun `test get MovieByGenre on Load Successfully`() = runBlocking {
        val response = MovieByGenreResponse(
            page = 1,
            totalResults = 1,
            results = listOf(
                MovieResultsItem(
                    id = 1,
                    title = "Lorem ipsum",
                    posterPath = "%%%%%%"
                ),
                MovieResultsItem(
                    id = 2,
                    title = "Lorem ipsum",
                    posterPath = "%%%%%%"
                ),
                MovieResultsItem(
                    id = 3,
                    title = "Lorem ipsum",
                    posterPath = "%%%%%%"
                )
            ),
            totalPages = 3
        )

        val expectedResult = PagingSource.LoadResult.Page(
                data = movieItem,
                prevKey = null,
                nextKey = 2
        )

        coEvery {
            service.listMovieByGenre(1, "1")
        } returns Response.success(response)

        val result = sut.listMovieByGenre("1").load(
            PagingSource.LoadParams.Append(
                key = 1,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        assertEquals(expectedResult, result)

        coVerify(exactly = 1) {
            service.listMovieByGenre(1, "1")
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

    @Test
    fun `test get movie detail on Success 200`() = runBlocking {
        val response = Response.success(MovieDetailResponse(
            id = 1,
            title = "Iron Man",
            genres = listOf(),
            overview = "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum",
            posterPath = "www.tmdb.com",
            releaseDate = "25 Jan 2008",
            voteAverage = 8.7,
            status = "release"
        ))

        coEvery {
            service.movieDetail("1")
        } returns response

        sut.getMovieDetail("1").test {
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
            service.movieDetail("1")
        }

        confirmVerified(service)
    }

    @Test
    fun `test get MovieReview on Failure Timeout`() = runBlocking {
        val expectedResult = PagingSource.LoadResult.Error<Int, MovieReviewItem>(SocketTimeoutException())

        coEvery {
            service.listMovieReview("1",1)
        } throws expectedResult.throwable

        val result = sut.listMovieReview("1").load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        assertEquals(expectedResult, result)

        coVerify(exactly = 1) {
            service.listMovieReview("1", 1)
        }

        confirmVerified(service)
    }

    @Test
    fun `test get MovieReview on Failure Connectivity`() = runBlocking {
        val expectedResult = PagingSource.LoadResult.Error<Int, MovieReviewItem>(IOException())

        coEvery {
            service.listMovieReview("1",1)
        } throws expectedResult.throwable

        val result = sut.listMovieReview("1").load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        assertEquals(expectedResult, result)

        coVerify(exactly = 1) {
            service.listMovieReview("1", 1)
        }

        confirmVerified(service)
    }

    @Test
    fun `test get MovieReview on Failure HttpException`() = runBlocking {

        val exception = HttpException(Response.error<MoviesService>(404, "".toResponseBody()))
        val expectedResult = PagingSource.LoadResult.Error<Int, MovieReviewItem>(exception)

        coEvery { service.listMovieReview("1",1) } throws expectedResult.throwable

        val result = sut.listMovieReview("1").load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        assertEquals(expectedResult, result)

        coVerify {
            service.listMovieReview("1", 1)
        }

        confirmVerified(service)
    }

    @Test
    fun `test get MovieReview on Load Successfully`() = runBlocking {
        val response = MovieReviewResponse(
            page = 1,
            totalResults = 1,
            results = listOf(
                MovieReviewResult(
                    authorDetails = AuthorDetails(
                        avatarPath = "Lorem ipsum",
                        name = "Lorem ipsum",
                        rating = 0.0,
                        username = "lorem ipsum"
                    ),
                    author = "Lorem ipsum",
                    createdAt = "Lorem ipsum",
                    id = "12",
                    content = "Lorem ipsum"
                ),
                MovieReviewResult(
                    authorDetails = AuthorDetails(
                        avatarPath = "Lorem ipsum",
                        name = "Lorem ipsum",
                        rating = 0.0,
                        username = "lorem ipsum"
                    ),
                    author = "Lorem ipsum",
                    createdAt = "Lorem ipsum",
                    id = "12",
                    content = "Lorem ipsum"
                ),
                MovieReviewResult(
                    authorDetails = AuthorDetails(
                        avatarPath = "Lorem ipsum",
                        name = "Lorem ipsum",
                        rating = 0.0,
                        username = "lorem ipsum"
                    ),
                    author = "Lorem ipsum",
                    createdAt = "Lorem ipsum",
                    id = "12",
                    content = "Lorem ipsum"
                )
            ),
            totalPages = 3
        )

        val expectedResult = PagingSource.LoadResult.Page(
            data = movieReviewItem,
            prevKey = null,
            nextKey = 2
        )

        coEvery {
            service.listMovieReview("1", 1)
        } returns Response.success(response)

        val result = sut.listMovieReview("1").load(
            PagingSource.LoadParams.Append(
                key = 1,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        assertEquals(expectedResult, result)

        coVerify(exactly = 1) {
            service.listMovieReview("1", 1)
        }

        confirmVerified(service)
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