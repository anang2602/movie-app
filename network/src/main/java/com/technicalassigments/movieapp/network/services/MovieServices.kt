package com.technicalassigments.movieapp.network.services

import com.technicalassigments.movieapp.network.BuildConfig
import com.technicalassigments.movieapp.network.dto.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieServices {

    @GET("discover/movie")
    suspend fun getMovieByGenre(
        @Query("api_key") apiKey: String = BuildConfig.MY_API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
        @Query("with_genres") genres: String
    ): MovieResponse

    @GET("search/movie")
    suspend fun getMovieBySearchQuery(
        @Query("api_key") apiKey: String = BuildConfig.MY_API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
        @Query("query") query: String
    ): MovieResponse

}