package com.technicalassigments.movieapp.domain.usecase

import androidx.paging.PagingData
import com.technicalassigments.movieapp.network.dto.ReviewsResponse
import kotlinx.coroutines.flow.Flow

interface FetchReviewsUseCase {

    fun fetchReviewsByMovieId(movieId: Int): Flow<PagingData<ReviewsResponse.Reviews>>

}