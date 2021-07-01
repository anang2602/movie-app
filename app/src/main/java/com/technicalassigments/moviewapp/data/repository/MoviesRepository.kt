package com.technicalassigments.moviewapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.technicalassigments.moviewapp.data.api.ApiService
import com.technicalassigments.moviewapp.data.model.Movie
import kotlinx.coroutines.flow.Flow

class MoviesRepository(private val apiService: ApiService) {

    fun getMoviesResultStream(genre: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { MoviesPagingSource(apiService, genre) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

}