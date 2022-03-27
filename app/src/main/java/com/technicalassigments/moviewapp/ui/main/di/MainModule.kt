package com.technicalassigments.moviewapp.ui.main.di

import com.technicalassigments.movieapp.cache.database.dao.GenreDao
import com.technicalassigments.moviewapp.utils.viewModel
import com.technicalassigments.movieapp.domain.repository.genre.GenreRepository
import com.technicalassigments.movieapp.domain.repository.movie.MovieRepository
import com.technicalassigments.movieapp.domain.usecase.FetchGenreUseCaseImpl
import com.technicalassigments.movieapp.domain.usecase.FetchMovieUseCaseImpl
import com.technicalassigments.movieapp.domain.utils.NetworkUtils
import com.technicalassigments.movieapp.network.services.GenreServices
import com.technicalassigments.movieapp.network.services.MovieServices
import com.technicalassigments.moviewapp.di.scope.FeatureScope
import com.technicalassigments.moviewapp.ui.main.view.MainActivity
import com.technicalassigments.moviewapp.ui.main.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class MainModule(
    private val activity: MainActivity
) {

    @Provides
    @FeatureScope
    fun provideGenreRepository(
        networkUtils: NetworkUtils,
        genreServices: GenreServices,
        genreDao: GenreDao
    ) = GenreRepository(networkUtils, genreServices, genreDao)

    @Provides
    @FeatureScope
    fun provideMovieRepository(
        movieServices: MovieServices
    ) = MovieRepository(movieServices)

    @Provides
    @FeatureScope
    fun provideGenreUseCaseImpl(
        repository: GenreRepository
    ) = FetchGenreUseCaseImpl(repository)

    @Provides
    @FeatureScope
    fun provideMovieUseCaseImpl(
        repository: MovieRepository
    ) = FetchMovieUseCaseImpl(repository)

    @Provides
    @FeatureScope
    fun provideViewModel(
        fetchGenreUseCaseImpl: FetchGenreUseCaseImpl,
        fetchMovieUseCaseImpl: FetchMovieUseCaseImpl
    ) = activity.viewModel {
        MainViewModel(fetchGenreUseCaseImpl, fetchMovieUseCaseImpl)
    }

}