package com.technicalassigments.moviewapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.technicalassigments.moviewapp.BuildConfig
import com.technicalassigments.moviewapp.data.api.ApiService
import com.technicalassigments.moviewapp.data.model.Reviews
import retrofit2.HttpException
import java.io.IOException

private const val REVIEWS_STARTING_PAGE_INDEX = 1
private const val LANGUAGE = "en-US"

class ReviewsPagingSource(
    private val service: ApiService,
    private val movie_id: Int
) : PagingSource<Int, Reviews>() {

    override fun getRefreshKey(state: PagingState<Int, Reviews>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Reviews> {
        val position = params.key ?: REVIEWS_STARTING_PAGE_INDEX
        return try {
            val response =
                service.getMovieReviews(movie_id, BuildConfig.MY_API_KEY, LANGUAGE, position)
            val reviews = response.results ?: emptyList()
            val nextKey = if (reviews.isEmpty()) {
                null
            } else {
                position + 1
            }
            LoadResult.Page(
                data = reviews,
                prevKey = if (position == REVIEWS_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}