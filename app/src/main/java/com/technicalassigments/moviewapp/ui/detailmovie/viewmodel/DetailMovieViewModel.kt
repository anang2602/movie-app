package com.technicalassigments.moviewapp.ui.detailmovie.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.technicalassigments.moviewapp.data.model.Reviews
import com.technicalassigments.moviewapp.data.model.VideoResult
import com.technicalassigments.moviewapp.data.repository.ReviewsRepository
import com.technicalassigments.moviewapp.data.repository.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class DetailMovieViewModel(
    private val videoRepository: VideoRepository,
    private val reviewsRepository: ReviewsRepository
): ViewModel() {

    private val movieIdLiveData = MutableLiveData<Int>()
    val videoResult: LiveData<VideoResult> = movieIdLiveData.switchMap { id ->
        liveData {
            val videos = videoRepository.getMoviesVideo(id).asLiveData(Dispatchers.Main)
            emitSource(videos)
        }
    }

    fun getVideos(movieId: Int) {
        movieIdLiveData.postValue(movieId)
    }

    private var currentReviewsResult: Flow<PagingData<Reviews>>? = null

    fun getReviews(movie_id: Int): Flow<PagingData<Reviews>> {
        val lastResult = currentReviewsResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult: Flow<PagingData<Reviews>> = reviewsRepository.getReviews(movie_id)
            .cachedIn(viewModelScope)
        currentReviewsResult = newResult
        return newResult
    }

}