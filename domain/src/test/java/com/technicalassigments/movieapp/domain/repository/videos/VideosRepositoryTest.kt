package com.technicalassigments.movieapp.domain.repository.videos

import com.google.common.truth.Truth.assertThat
import com.technicalassigments.movieapp.domain.TestCoroutineRule
import com.technicalassigments.movieapp.domain.utils.NetworkUtils
import com.technicalassigments.movieapp.domain.utils.Resource
import com.technicalassigments.movieapp.domain.utils.Status
import com.technicalassigments.movieapp.network.dto.VideoResponse
import com.technicalassigments.movieapp.network.services.VideoService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class VideosRepositoryTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val coroutineDispatcher = TestCoroutineDispatcher()

    private val networkUtils = mockk<NetworkUtils>()

    private val videoService = mockk<VideoService>()

    private val videosRepository = VideosRepository(
        networkUtils, videoService, coroutineDispatcher
    )

    @Test
    fun `should return loading and success if internet avaiable`() = testCoroutineRule.runBlockingTest {
        // given
        val fakeId = 1
        val fakeResult = listOf(
            VideoResponse.Video(
                "fake", "fake", "fake", "fake",
                "fake", "fake", 1, "fake"
            )
        )
        val fakeSuccessResponse = VideoResponse(
            1,
            fakeResult
        )
        every { networkUtils.checkForInternet() } returns true
        coEvery { videoService.getMovieVideos(fakeId) } returns Response.success(fakeSuccessResponse)

        // when
        val result = videosRepository.fetchVideoById(fakeId).toList()

        // then
        coVerify(exactly = 1) { videoService.getMovieVideos(fakeId) }

        assertThat(result).containsExactly(
            Resource(Status.LOADING, null, null),
            Resource(Status.SUCCESS, fakeResult, null)
        ).inOrder()
    }

}