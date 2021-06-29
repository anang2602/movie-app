package com.technicalassigments.moviewapp.data.api

import com.mindorks.framework.mvvm.data.model.GenreResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("genre/movie/list")
    fun getMoviesGenre(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<GenreResponse>

}