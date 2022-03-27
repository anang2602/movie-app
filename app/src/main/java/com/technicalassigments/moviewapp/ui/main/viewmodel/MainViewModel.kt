package com.technicalassigments.moviewapp.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.technicalassigments.movieapp.domain.usecase.FetchGenreUseCaseImpl
import com.technicalassigments.movieapp.domain.usecase.FetchMovieUseCaseImpl
import com.technicalassigments.movieapp.network.dto.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class MainViewModel(
    private val fetchGenreUseCaseImpl: FetchGenreUseCaseImpl,
    private val fetchMovieUseCaseImpl: FetchMovieUseCaseImpl
): ViewModel() {

    private var currentGenreValue: String? = null
    private var currentMovieResult: Flow<PagingData<MovieResponse.Movie>>? = null

    val fetchGenre = liveData {
        val letGenreFlow = fetchGenreUseCaseImpl.retrieveGenre().asLiveData(Dispatchers.Main)
        emitSource(letGenreFlow)
    }

    fun getMovies(genre: String): Flow<PagingData<MovieResponse.Movie>> {
        val lastResult = currentMovieResult
        if (genre == currentGenreValue && lastResult != null) {
            return lastResult
        }
        currentGenreValue = genre
        val newResult: Flow<PagingData<MovieResponse.Movie>> = fetchMovieUseCaseImpl.fetchMovieByGenre(genre)
            .cachedIn(viewModelScope)
        currentMovieResult = newResult
        return newResult
    }

}