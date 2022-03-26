package com.technicalassigments.movieapp.domain.usecase

class FetchGenreUseCaseImpl(
    private val fetchGenreUseCase: FetchGenreUseCase
) {

    suspend fun retrieveGenre() = fetchGenreUseCase.fetchGenreMovie()

}