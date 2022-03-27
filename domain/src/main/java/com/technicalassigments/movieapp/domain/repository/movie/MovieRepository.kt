package com.technicalassigments.movieapp.domain.repository.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.technicalassigments.movieapp.domain.usecase.FetchMovieUseCase
import com.technicalassigments.movieapp.network.dto.MovieResponse
import com.technicalassigments.movieapp.network.services.MovieServices
import kotlinx.coroutines.flow.Flow

class MovieRepository(
    private val movieServices: MovieServices,
): FetchMovieUseCase {

    override fun fetchMoviesByGenre(genre: String): Flow<PagingData<MovieResponse.Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { MoviesPagingSource(movieServices, genre = genre) }
        ).flow
    }

    override fun fetchMoviesByQuery(query: String): Flow<PagingData<MovieResponse.Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { MoviesPagingSource(movieServices, query = query) }
        ).flow
    }

}