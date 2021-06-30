package com.technicalassigments.moviewapp.ui.searchable.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.technicalassigments.moviewapp.data.model.Movie
import com.technicalassigments.moviewapp.data.repository.SearchMovieRepository
import kotlinx.coroutines.flow.Flow

class SearchViewModel(
    private val searchMovieRepository: SearchMovieRepository
): ViewModel() {

    private var currentQueryValue: String? = null
    private var currentMovieResult: Flow<PagingData<Movie>>? = null

    fun getMovies(query: String): Flow<PagingData<Movie>> {
        val lastResult = currentMovieResult
        if (query == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = query
        val newResult: Flow<PagingData<Movie>> = searchMovieRepository.getMoviesResultStream(query)
            .cachedIn(viewModelScope)
        currentMovieResult = newResult
        return newResult
    }

}