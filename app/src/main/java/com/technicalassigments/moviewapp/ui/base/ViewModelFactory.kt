package com.technicalassigments.moviewapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.technicalassigments.moviewapp.data.api.ApiService
import com.technicalassigments.moviewapp.data.repository.GenreRepository
import com.technicalassigments.moviewapp.data.repository.MoviesRepository
import com.technicalassigments.moviewapp.data.repository.SearchMovieRepository
import com.technicalassigments.moviewapp.ui.main.viewmodel.MainViewModel
import com.technicalassigments.moviewapp.ui.searchable.viewmodel.SearchViewModel

class ViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(GenreRepository(apiService), MoviesRepository(apiService)) as T
        }
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(SearchMovieRepository(apiService)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}