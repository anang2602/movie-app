package com.technicalassigments.movieapp.domain.usecase

class FetchMovieUseCaseImpl(
    private val fetchMovieUseCase: FetchMovieUseCase
) {

    fun fetchMovieByGenre(genre: String) = fetchMovieUseCase.fetchMoviesByGenre(genre)

    fun fetchMovieByQuery(query: String) = fetchMovieUseCase.fetchMoviesByQuery(query)

}