package com.technicalassigments.moviewapp.data.repository

import com.technicalassigments.moviewapp.data.api.ApiHelper
import com.technicalassigments.moviewapp.data.model.MovieResponse
import io.reactivex.Observable
import java.util.*

class MainRepository(private val apiHelper: ApiHelper) {

    fun getUpcomingMovies(page: Int): Observable<MovieResponse> {
        return apiHelper.getUpcomingMovies(page)
    }

}