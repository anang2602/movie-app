package com.technicalassigments.movieapp.domain.usecase

import androidx.paging.PagingData
import com.technicalassigments.movieapp.network.dto.MovieResponse
import kotlinx.coroutines.flow.Flow

interface FetchMovieUseCase {

    fun fetchMoviesByGenre(genre: String): Flow<PagingData<MovieResponse.Movie>>

    fun fetchMoviesByQuery(query: String): Flow<PagingData<MovieResponse.Movie>>

}