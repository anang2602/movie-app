package com.technicalassigments.moviewapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.technicalassigments.moviewapp.data.api.ApiService
import com.technicalassigments.moviewapp.data.model.Reviews
import kotlinx.coroutines.flow.Flow

class ReviewsRepository(private val apiService: ApiService) {
    fun getReviews(movie_id: Int): Flow<PagingData<Reviews>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { ReviewsPagingSource(apiService, movie_id) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

}