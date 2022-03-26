package com.technicalassigments.movieapp.network.services

import com.technicalassigments.movieapp.network.BuildConfig
import com.technicalassigments.movieapp.network.dto.GenreResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreServices {

    @GET("genre/movie/list")
    suspend fun getMoviesGenre(
        @Query("api_key") apiKey: String = BuildConfig.MY_API_KEY,
        @Query("language") language: String = "en-US"
    ): Response<GenreResponse>

}