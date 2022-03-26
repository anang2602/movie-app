package com.technicalassigments.movieapp.network.services

import com.technicalassigments.movieapp.network.BuildConfig
import com.technicalassigments.movieapp.network.dto.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VideoService {

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") apiKey: String = BuildConfig.MY_API_KEY,
        @Query("language") language: String = "en-US"
    ): Response<VideoResponse>

}