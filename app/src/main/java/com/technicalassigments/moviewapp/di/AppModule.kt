package com.technicalassigments.moviewapp.di

import android.app.Application
import android.content.Context
import com.technicalassigments.moviewapp.MovieApp
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(application: MovieApp): Context = application.applicationContext

}