package com.technicalassigments.moviewapp.ui.main.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.technicalassigments.moviewapp.data.model.GenreResult
import com.technicalassigments.moviewapp.data.model.Movie
import com.technicalassigments.moviewapp.data.repository.GenreRepository
import com.technicalassigments.moviewapp.data.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class MainViewModel(
    private val genreRepositori: GenreRepository,
    private val moviesRepositori: MoviesRepository
) : ViewModel() {

    val genreResult: LiveData<GenreResult> = liveData {
        val genres = genreRepositori.getGenreResultStream().asLiveData(Dispatchers.Main)
        emitSource(genres)
    }

    private var currentGenreValue: String? = null
    private var currentMovieResult: Flow<PagingData<Movie>>? = null

    fun getMovies(genre: String): Flow<PagingData<Movie>> {
        val lastResult = currentMovieResult
        if (genre == currentGenreValue && lastResult != null) {
            return lastResult
        }
        currentGenreValue = genre
        val newResult: Flow<PagingData<Movie>> = moviesRepositori.getMoviesResultStream(genre)
            .cachedIn(viewModelScope)
        currentMovieResult = newResult
        return newResult
    }

}