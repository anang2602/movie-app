package com.technicalassigments.movieapp.domain.usecase

class FetchVideoUseCaseImpl(
    private val fetchVideoUseCase: FetchVideoUseCase
) {

    suspend fun fetchVideoById(movieId: Int) = fetchVideoUseCase.fetchVideoById(movieId)

}