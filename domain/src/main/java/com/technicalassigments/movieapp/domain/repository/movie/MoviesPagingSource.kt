package com.technicalassigments.movieapp.domain.repository.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.technicalassigments.movieapp.network.dto.MovieResponse
import com.technicalassigments.movieapp.network.services.MovieServices
import retrofit2.HttpException
import java.io.IOException

class MoviesPagingSource(
    private val service: MovieServices,
    private val genre: String? = null,
    private val query: String? = null
) : PagingSource<Int, MovieResponse.Movie>() {

    private val startingIndex = 1
    private val pagingSize = 20

    override fun getRefreshKey(state: PagingState<Int, MovieResponse.Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse.Movie> {
        val position = params.key ?: startingIndex
        return try {
            val response = if (genre != null) {
                service.getMovieByGenre(genres = genre, page = position)
            } else {
                service.getMovieBySearchQuery(query = query ?: "", page = position)
            }
            val movies = response.results ?: emptyList()
            val nextKey = if (movies.isEmpty()) {
                null
            } else {
                position + (params.loadSize / pagingSize)
            }
            LoadResult.Page(
                data = movies,
                prevKey = if (position == startingIndex) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}