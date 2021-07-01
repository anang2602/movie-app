package com.technicalassigments.moviewapp.data.repository

import com.technicalassigments.moviewapp.BuildConfig
import com.technicalassigments.moviewapp.data.api.ApiService
import com.technicalassigments.moviewapp.data.model.VideoResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.HttpException
import java.io.IOException


private const val LANGUAGE = "en-US"
class VideoRepository(private val service: ApiService) {

    private val videoResult = MutableSharedFlow<VideoResult>(replay = 1)
    private var isRequestInProgress = false

    suspend fun getMoviesVideo(movie_id: Int): Flow<VideoResult> {
        requestAndSaveData(movie_id)
        return videoResult
    }

    private suspend fun requestAndSaveData(movie_id: Int): Boolean {
        isRequestInProgress = true
        var successful = false

        try {
            val response = service.getMovieVideos(
                movie_id,
                BuildConfig.MY_API_KEY,
                LANGUAGE,
            )
            val videos = response.results ?: emptyList()
            videoResult.emit(VideoResult.Success(videos))
            successful = true
        } catch (e: IOException) {
            videoResult.emit(VideoResult.Error(e))
        } catch (e: HttpException) {
            videoResult.emit(VideoResult.Error(e))
        }
        return successful
    }

}