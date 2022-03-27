package com.technicalassigments.movieapp.domain.usecase

class FetchReviewsUseCaseImpl(
    private val fetchReviewsUseCase: FetchReviewsUseCase
) {

    fun fetchReviewsByMovieId(movieId: Int) = fetchReviewsUseCase.fetchReviewsByMovieId(movieId)

}