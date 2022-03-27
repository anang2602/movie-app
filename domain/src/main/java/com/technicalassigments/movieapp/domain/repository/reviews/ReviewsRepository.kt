package com.technicalassigments.movieapp.domain.repository.reviews

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.technicalassigments.movieapp.domain.usecase.FetchReviewsUseCase
import com.technicalassigments.movieapp.network.dto.ReviewsResponse
import com.technicalassigments.movieapp.network.services.ReviewsService
import kotlinx.coroutines.flow.Flow

class ReviewsRepository(private val apiService: ReviewsService) : FetchReviewsUseCase {

    override fun fetchReviewsByMovieId(movieId: Int): Flow<PagingData<ReviewsResponse.Reviews>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { ReviewsPagingSource(apiService, movieId) }
        ).flow
    }


}