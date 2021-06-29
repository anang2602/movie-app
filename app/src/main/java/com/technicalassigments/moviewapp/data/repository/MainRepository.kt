package com.technicalassigments.moviewapp.data.repository

import com.mindorks.framework.mvvm.data.model.GenreResponse
import com.technicalassigments.moviewapp.data.api.ApiHelper
import io.reactivex.Observable

class MainRepository(private val apiHelper: ApiHelper) {

    fun getMoviesGenre(): Observable<GenreResponse> {
        return apiHelper.getMoviesGenre()
    }

}