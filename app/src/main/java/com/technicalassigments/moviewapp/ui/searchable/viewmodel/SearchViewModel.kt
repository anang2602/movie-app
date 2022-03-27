package com.technicalassigments.moviewapp.ui.searchable.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.technicalassigments.movieapp.domain.usecase.FetchMovieUseCaseImpl
import com.technicalassigments.movieapp.network.dto.MovieResponse
import kotlinx.coroutines.flow.Flow

class SearchViewModel(
    private val fetchMovieUseCaseImpl: FetchMovieUseCaseImpl
) : ViewModel() {

    private var currentQueryValue: String? = null
    private var currentMovieResult: Flow<PagingData<MovieResponse.Movie>>? = null

    fun getMovies(query: String): Flow<PagingData<MovieResponse.Movie>> {
        val lastResult = currentMovieResult
        if (query == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = query
        val newResult: Flow<PagingData<MovieResponse.Movie>> =
            fetchMovieUseCaseImpl.fetchMovieByQuery(query)
                .cachedIn(viewModelScope)
        currentMovieResult = newResult
        return newResult
    }

}