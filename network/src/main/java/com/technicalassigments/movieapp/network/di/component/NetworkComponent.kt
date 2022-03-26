package com.technicalassigments.movieapp.network.di.component

import com.technicalassigments.movieapp.network.di.module.NetworkModule
import com.technicalassigments.movieapp.network.services.GenreServices
import com.technicalassigments.movieapp.network.services.MovieServices
import com.technicalassigments.movieapp.network.services.ReviewsService
import com.technicalassigments.movieapp.network.services.VideoService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class
    ]
)
interface NetworkComponent {

    fun genreService(): GenreServices

    fun movieService(): MovieServices

    fun videoService(): VideoService

    fun reviewsService(): ReviewsService

}