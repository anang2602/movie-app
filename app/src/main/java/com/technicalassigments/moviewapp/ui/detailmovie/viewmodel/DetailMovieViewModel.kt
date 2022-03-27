package com.technicalassigments.moviewapp.ui.detailmovie.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.technicalassigments.movieapp.domain.usecase.FetchReviewsUseCaseImpl
import com.technicalassigments.movieapp.domain.usecase.FetchVideoUseCaseImpl
import com.technicalassigments.movieapp.network.dto.ReviewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class DetailMovieViewModel(
    private val fetchVideoUseCaseImpl: FetchVideoUseCaseImpl,
    private val fetchReviewsUseCaseImpl: FetchReviewsUseCaseImpl
) : ViewModel() {

    private val movieIdLiveData = MutableLiveData<Int>()

    val videoResult = movieIdLiveData.switchMap {
        liveData {
            val letVideoFlow = fetchVideoUseCaseImpl.fetchVideoById(it).asLiveData(Dispatchers.Main)
            emitSource(letVideoFlow)
        }
    }


    fun getVideos(movieId: Int) {
        movieIdLiveData.postValue(movieId)
    }

    private var currentReviewsResult: Flow<PagingData<ReviewsResponse.Reviews>>? = null

    fun getReviews(movie_id: Int): Flow<PagingData<ReviewsResponse.Reviews>> {
        val lastResult = currentReviewsResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult: Flow<PagingData<ReviewsResponse.Reviews>> =
            fetchReviewsUseCaseImpl.fetchReviewsByMovieId(movie_id)
                .cachedIn(viewModelScope)
        currentReviewsResult = newResult
        return newResult
    }

}