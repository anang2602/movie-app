package com.technicalassigments.movieapp.cache.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.technicalassigments.movieapp.cache.database.MovieDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideMovieDatabase(context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "moviedb"
        ).build()
    }

    @Provides
    @Singleton
    fun provideGenreDao(moviedb: MovieDatabase) = moviedb.genreDao()

}