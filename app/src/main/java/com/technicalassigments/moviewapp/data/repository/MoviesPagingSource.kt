package com.technicalassigments.moviewapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.technicalassigments.moviewapp.BuildConfig
import com.technicalassigments.moviewapp.data.api.ApiService
import com.technicalassigments.moviewapp.data.model.Movie
import com.technicalassigments.moviewapp.data.repository.MoviesRepository.Companion.NETWORK_PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException


private const val MOVIES_STARTING_PAGE_INDEX = 1
private const val LANGUAGE = "en-US"

class MoviesPagingSource(
    private val service: ApiService,
    private val genre: String
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: MOVIES_STARTING_PAGE_INDEX
        return try {
            val response =
                service.getMovieByGenre(BuildConfig.MY_API_KEY, LANGUAGE, position, genre)
            val movies = response.results ?: emptyList()
            val nextKey = if (movies.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = movies,
                prevKey = if (position == MOVIES_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}