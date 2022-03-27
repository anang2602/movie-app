package com.technicalassigments.movieapp.domain.repository.movie

import androidx.paging.PagingSource
import com.technicalassigments.movieapp.domain.TestCoroutineRule
import com.technicalassigments.movieapp.network.dto.MovieResponse
import com.technicalassigments.movieapp.network.services.MovieServices
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesPagingSourceTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockMovie = listOf(
        MovieResponse.Movie(
            null, null, null,
            1, null, null, null,
            null, null, null, null, null,
            null, null
        ),
        MovieResponse.Movie(
            null, null, null,
            2, null, null, null,
            null, null, null, null, null,
            null, null
        )
    )

    private val mockMovieResponse = MovieResponse(
        1,
        listOf(mockMovie[0], mockMovie[1]),
        3,
        1
    )

    private val movieServices = mockk<MovieServices>()

    @Test
    fun `should load page when successful load data`() = testCoroutineRule.runBlockingTest {
        //given
        val fakeGenre = "fakeGenre"
        coEvery { movieServices.getMovieByGenre(genres = fakeGenre, page = 1) } returns mockMovieResponse
        // when
        val pagingSource = MoviesPagingSource(movieServices, fakeGenre)
        // then
        assertEquals(
            PagingSource.LoadResult.Page(
                data = listOf(mockMovie[0], mockMovie[1]),
                prevKey = null,
                nextKey = 1
            ),
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        )
    }

}