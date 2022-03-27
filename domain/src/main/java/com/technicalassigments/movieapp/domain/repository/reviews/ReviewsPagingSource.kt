package com.technicalassigments.movieapp.domain.repository.reviews

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.technicalassigments.movieapp.network.dto.ReviewsResponse
import com.technicalassigments.movieapp.network.services.ReviewsService
import retrofit2.HttpException
import java.io.IOException

class ReviewsPagingSource(
    private val service: ReviewsService,
    private val movie_id: Int
) : PagingSource<Int, ReviewsResponse.Reviews>() {

    private val reviewStartingPageIndex = 1

    override fun getRefreshKey(state: PagingState<Int, ReviewsResponse.Reviews>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewsResponse.Reviews> {
        val position = params.key ?: reviewStartingPageIndex
        return try {
            val response =
                service.getMovieReviews(movie_id = movie_id, page = position)
            val reviews = response.results ?: emptyList()
            val nextKey = if (reviews.isEmpty()) {
                null
            } else {
                position + 1
            }
            LoadResult.Page(
                data = reviews,
                prevKey = if (position == reviewStartingPageIndex) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}