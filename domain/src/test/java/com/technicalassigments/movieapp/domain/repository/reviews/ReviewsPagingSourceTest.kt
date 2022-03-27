package com.technicalassigments.movieapp.domain.repository.reviews

import androidx.paging.PagingSource
import com.technicalassigments.movieapp.domain.TestCoroutineRule
import com.technicalassigments.movieapp.network.dto.ReviewsResponse
import com.technicalassigments.movieapp.network.services.ReviewsService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ReviewsPagingSourceTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockResponse = ReviewsResponse(
        1,
        1,
        listOf(
            ReviewsResponse.Reviews(
                "",
                ReviewsResponse.AuthorDetails("", "", "", ""),
                "", "", "", "", "",
            ),
            ReviewsResponse.Reviews(
                "",
                ReviewsResponse.AuthorDetails("", "", "", ""),
                "", "", "", "", "",
            )
        ),
        3,
        2
    )

    private val reviewServies = mockk<ReviewsService>()

    @Test
    fun `should load page when successful load data`() = testCoroutineRule.runBlockingTest {
        //given
        val fakeId = 1
        coEvery { reviewServies.getMovieReviews(movie_id = fakeId, page = 1) } returns mockResponse
        // when
        val pagingSource = ReviewsPagingSource(reviewServies, fakeId)
        // then
        Assert.assertEquals(
            PagingSource.LoadResult.Page(
                data = listOf(mockResponse.results!![0], mockResponse.results!![1]),
                prevKey = null,
                nextKey = 2
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