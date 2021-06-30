package com.technicalassigments.moviewapp.data.api

import com.technicalassigments.moviewapp.data.model.Reviews

data class ReviewsResponse(
    var id: Int?,
    var page: Int?,
    var results: List<Reviews>?,
    var total_pages: Int?,
    var total_results: Int?
)