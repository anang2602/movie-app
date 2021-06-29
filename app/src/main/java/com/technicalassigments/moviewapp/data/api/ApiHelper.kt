package com.technicalassigments.moviewapp.data.api

import com.technicalassigments.moviewapp.BuildConfig

class ApiHelper(private val apiService: ApiService) {

    companion object {
        const val language = "en-US"
    }

    fun getMoviesGenre() =
        apiService.getMoviesGenre(BuildConfig.MY_API_KEY, language)

}