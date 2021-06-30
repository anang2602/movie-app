package com.technicalassigments.moviewapp.data.model

import java.lang.Exception

sealed class DiscoverMovieResult {
    data class Success(val data: List<Movie>): DiscoverMovieResult()
    data class Error(val error: Exception): DiscoverMovieResult()
}