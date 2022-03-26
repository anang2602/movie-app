package com.technicalassigments.movieapp.network.services

import com.technicalassigments.movieapp.network.BuildConfig
import com.technicalassigments.movieapp.network.dto.ReviewsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewsService {

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movie_id: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.MY_API_KEY,
        @Query("language") language: String = "en-US"
    ): ReviewsResponse

}