package com.technicalassigments.moviewapp.data.api

import com.technicalassigments.moviewapp.data.model.Video

data class VideoResponse(
    var id: Int?,
    var results: List<Video>?
)