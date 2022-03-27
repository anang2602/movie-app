package com.technicalassigments.moviewapp.ui.detailmovie.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.technicalassigments.movieapp.domain.repository.reviews.ReviewsRepository
import com.technicalassigments.movieapp.domain.repository.videos.VideosRepository
import com.technicalassigments.movieapp.domain.usecase.FetchReviewsUseCaseImpl
import com.technicalassigments.movieapp.domain.usecase.FetchVideoUseCaseImpl
import com.technicalassigments.moviewapp.TestCoroutineRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@ExperimentalCoroutinesApi
class DetailMovieViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val reviewRepository = mockk<ReviewsRepository>()
    private val videoRepository = mockk<VideosRepository>()

    private val reviewUseCaseImpl = FetchReviewsUseCaseImpl(reviewRepository)
    private val videosUseCaseImpl = FetchVideoUseCaseImpl(videoRepository)

}