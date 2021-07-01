package com.technicalassigments.moviewapp.data.model

import java.lang.Exception

sealed class VideoResult {
    data class Success(val data: List<Video>): VideoResult()
    data class Error(val error: Exception): VideoResult()
}