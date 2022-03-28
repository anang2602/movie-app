package com.technicalassigments.moviewapp.ui.detailmovie.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.technicalassigments.movieapp.domain.usecase.FetchReviewsUseCaseImpl
import com.technicalassigments.movieapp.domain.usecase.FetchVideoUseCaseImpl
import com.technicalassigments.movieapp.domain.utils.Resource
import com.technicalassigments.movieapp.network.dto.VideoResponse
import com.technicalassigments.moviewapp.TestCoroutineRule
import io.mockk.*
import io.mockk.impl.annotations.OverrideMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailMovieViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @RelaxedMockK
    lateinit var observer: Observer<Resource<Collection<VideoResponse.Video>>>

    @RelaxedMockK
    lateinit var fetchVideoUseCaseImpl: FetchVideoUseCaseImpl

    @RelaxedMockK
    lateinit var fetchReviewsUseCaseImpl: FetchReviewsUseCaseImpl

    @OverrideMockKs
    lateinit var viewModel: DetailMovieViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `should fetch the video from use case`() = testCoroutineRule.runBlockingTest {
        // given
        val fakeId = 1
        val sampleData = listOf(
            VideoResponse.Video(
                "", "", "", "", "", "", 1, ""
            )
        )
        val expected = flow { emit(Resource.success(sampleData)) }
        coEvery { fetchVideoUseCaseImpl.fetchVideoById(any()) } answers { expected }
        // when
        viewModel.getVideos(fakeId)
        viewModel.videoResult.observeForever(observer)

        // then
        coVerify(exactly = 1) { fetchVideoUseCaseImpl.fetchVideoById(any()) }
        verify { observer.onChanged(Resource.success(sampleData)) }
        confirmVerified(fetchVideoUseCaseImpl, observer)
    }

    @Test
    fun `should fetch the reviews from use case`() = testCoroutineRule.runBlockingTest {
        // given
        val fakeId = 1
        // when
        viewModel.getReviews(fakeId)
        //then
        coVerify { fetchReviewsUseCaseImpl.fetchReviewsByMovieId(fakeId) }
    }

}