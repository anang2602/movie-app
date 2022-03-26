package com.technicalassigments.movieapp.network.di.module

import com.technicalassigments.movieapp.network.BuildConfig
import com.technicalassigments.movieapp.network.services.GenreServices
import com.technicalassigments.movieapp.network.services.MovieServices
import com.technicalassigments.movieapp.network.services.ReviewsService
import com.technicalassigments.movieapp.network.services.VideoService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        return interceptor
    }

    @Provides
    @Singleton
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideGenreService(retrofit: Retrofit): GenreServices =
        retrofit.create(GenreServices::class.java)

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieServices =
        retrofit.create(MovieServices::class.java)

    @Provides
    @Singleton
    fun provideVideoService(retrofit: Retrofit): VideoService =
        retrofit.create(VideoService::class.java)

    @Provides
    @Singleton
    fun provideReviewsService(retrofit: Retrofit): ReviewsService =
        retrofit.create(ReviewsService::class.java)
}