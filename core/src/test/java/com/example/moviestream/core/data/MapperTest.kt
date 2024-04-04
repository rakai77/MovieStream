package com.example.moviestream.core.data

import com.example.moviestream.core.data.remote.AuthorDetails
import com.example.moviestream.core.data.remote.GenresItemResult
import com.example.moviestream.core.data.remote.MovieByGenreResponse
import com.example.moviestream.core.data.remote.MovieDetailResponse
import com.example.moviestream.core.data.remote.MovieGenreResponse
import com.example.moviestream.core.data.remote.MovieResultsItem
import com.example.moviestream.core.data.remote.MovieReviewResponse
import com.example.moviestream.core.data.remote.MovieReviewResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotSame
import org.junit.Test

class MapperTest {

    @Test
    fun `map MovieGenreResponse to domain`() {
        val movieResponse = MovieGenreResponse(
            genres = listOf(
                GenresItemResult(
                    name = "horror",
                    id = 31
                ),
                GenresItemResult(
                    name = "action",
                    id = 24
                )
            )
        )

        val mapper = movieResponse.toDomain()

        assertEquals(2, mapper.genres.size)
        assertEquals("horror", mapper.genres[0].name)
        assertEquals("action", mapper.genres[1].name)
        assertEquals(31, mapper.genres[0].id)
        assertEquals(24, mapper.genres[1].id)
        assertNotSame(movieResponse.genres, mapper.genres)
    }

    @Test
    fun `map MovieGenreItem to domain`() {
        val movieGenreItem = GenresItemResult(
            name = "Horror",
            id = 1
        )

        val mapper = movieGenreItem.toDomain()

        assertEquals(movieGenreItem.name, mapper.name)
        assertEquals(movieGenreItem.id, mapper.id)
    }

    @Test
    fun `map MovieByGenreResponse to domain`() {
        val response = MovieByGenreResponse(
            page = 1,
            totalPages = 1,
            results = listOf(
                MovieResultsItem(
                    id = 19,
                    title = "Iron Man",
                    posterPath = "www.iron.com"
                ),
                MovieResultsItem(
                    id = 11,
                    title = "Avenger",
                    posterPath = "www.avenger.com"
                )
            ),
            totalResults = 2
        )

        val mapper = response.toDomain()

        assertEquals(response.page, mapper.page)
        assertEquals(response.totalPages, mapper.totalPages)
        assertEquals(response.totalResults, mapper.totalResults)
        assertEquals(response.results!!.size, mapper.results.size)
        assertNotSame(response.results , mapper.results)
    }

    @Test
    fun `map MovieResultItem to domain`() {
         val response = MovieResultsItem(
             id = 11,
             title = "Avenger",
             posterPath = "www.avenger.com"
         )

        val mapper = response.toDomain()

        assertEquals(response.id, mapper.id)
        assertEquals(response.title, mapper.title)
        assertEquals(response.posterPath, mapper.posterPath)
    }

    @Test
    fun `map MovieDetailResponse to Domain`() {
        val response = MovieDetailResponse(
            id = 1,
            title = "Iron Man",
            genres = listOf(),
            overview = "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum",
            posterPath = "www.tmdb.com",
            releaseDate = "25 Jan 2008",
            voteAverage = 8.7,
            status = "release"
        )

        val mapper = response.toDomain()

        assertEquals(response.id, mapper.id)
        assertEquals(response.title, mapper.title)
        assertEquals(response.genres!!.size, mapper.genres.size)
        assertEquals(response.overview, mapper.overview)
        assertEquals(response.posterPath, mapper.posterPath)
        assertEquals(response.releaseDate, mapper.releaseDate)
        assertEquals(response.voteAverage, mapper.voteAverage)
        assertEquals(response.status, mapper.status)
    }

    @Test
    fun `map MovieReviewResponse to domain`() {
        val response = MovieReviewResponse(
            page = 1,
            totalPages = 1,
            results = listOf(
                MovieReviewResult(
                    authorDetails = AuthorDetails(),
                    author = "James Gun",
                    createdAt = "24 Dec 1999",
                    id = "35",
                    content = "Nice !"
                ),
                MovieReviewResult(
                    authorDetails = AuthorDetails(),
                    author = "James Miller",
                    createdAt = "24 Oct 2009",
                    id = "42",
                    content = "Good !"
                )
            ),
            totalResults = 2
        )

        val mapper = response.toDomain()

        assertEquals(response.page, mapper.page)
        assertEquals(response.totalPages, mapper.totalPages)
        assertEquals(response.totalResults, mapper.totalResults)
        assertEquals(response.results!!.size, mapper.results.size)
        assertNotSame(response.results , mapper.results)
    }

    @Test
    fun `map AuthorDetails to domain`() {
        val response = AuthorDetails(
            avatarPath = "",
            name = "Dummy",
            rating = 9.0,
            username = "@Dummy"
        )

        val mapper = response.toDomain()

        assertEquals(response.avatarPath, mapper.avatarPath)
        assertEquals(response.name, mapper.name)
        assertEquals(response.rating, mapper.rating)
        assertEquals(response.username , mapper.username)
    }

}