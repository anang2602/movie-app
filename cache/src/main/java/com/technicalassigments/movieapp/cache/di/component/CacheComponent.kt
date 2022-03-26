package com.technicalassigments.movieapp.cache.di.component

import com.technicalassigments.movieapp.cache.database.dao.GenreDao
import com.technicalassigments.movieapp.cache.di.module.CacheModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CacheModule::class
    ]
)
interface CacheComponent {

    fun genreDao(): GenreDao

}
