package com.technicalassigments.movieapp.domain.repository.videos

import com.technicalassigments.movieapp.domain.usecase.FetchVideoUseCase
import com.technicalassigments.movieapp.domain.utils.ApiResponse
import com.technicalassigments.movieapp.domain.utils.ApiSuccessResponse
import com.technicalassigments.movieapp.domain.utils.NetworkUtils
import com.technicalassigments.movieapp.domain.utils.Resource
import com.technicalassigments.movieapp.network.dto.VideoResponse
import com.technicalassigments.movieapp.network.services.VideoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

class VideosRepository(
    private val networkUtils: NetworkUtils,
    private val videoService: VideoService
) : FetchVideoUseCase {

    override suspend fun fetchVideoById(movieId: Int): Flow<Resource<Collection<VideoResponse.Video>>> {
        return flow {
            emit(Resource.loading(null))
            if (networkUtils.checkForInternet()) {
                try {
                    val response = videoService.getMovieVideos(movieId)
                    val apiResponse = ApiResponse.create(response)
                    if (apiResponse is ApiSuccessResponse) {
                        emit(Resource.success(apiResponse.body.results))
                    }
                } catch (e: IOException) {
                    val apiResponse = ApiResponse.create<Throwable>(e)
                    emit(Resource.error(apiResponse.errorMessage, null))
                } catch (e: HttpException) {
                    val apiResponse = ApiResponse.create<Throwable>(e)
                    emit(Resource.error(apiResponse.errorMessage, null))
                }
            } else {
                emit(Resource.offline("Sedang offline", null))
            }
        }.flowOn(Dispatchers.IO)
    }
}