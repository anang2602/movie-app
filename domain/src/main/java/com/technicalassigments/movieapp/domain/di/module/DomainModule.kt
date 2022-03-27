package com.technicalassigments.movieapp.domain.di.module

import android.app.Application
import android.content.Context
import com.technicalassigments.movieapp.domain.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideApplicationContext(): Context = application

    @Singleton
    @Provides
    fun provideNetworkUtils(context: Context) = NetworkUtils(context)

}