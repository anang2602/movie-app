package com.technicalassigments.moviewapp.data.model

import java.lang.Exception

sealed class GenreResult {
    data class Success(val data: List<Genre>): GenreResult()
    data class Error(val error: Exception): GenreResult()
}