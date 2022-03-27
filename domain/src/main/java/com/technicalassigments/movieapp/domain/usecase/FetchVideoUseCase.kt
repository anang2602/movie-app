package com.technicalassigments.movieapp.domain.usecase

import com.technicalassigments.movieapp.domain.utils.Resource
import com.technicalassigments.movieapp.network.dto.VideoResponse
import kotlinx.coroutines.flow.Flow

interface FetchVideoUseCase {

    suspend fun fetchVideoById(movieId: Int): Flow<Resource<Collection<VideoResponse.Video>>>

}