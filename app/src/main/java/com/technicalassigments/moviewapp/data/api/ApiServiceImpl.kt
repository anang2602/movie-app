package com.technicalassigments.moviewapp.data.api

import android.util.Log
import com.google.gson.GsonBuilder
import com.technicalassigments.moviewapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiServiceImpl {

    companion object {
        fun create() : ApiService {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build()

            val gson = GsonBuilder().create()
            Log.d("Abnjing", BuildConfig.BASE_URL)
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl(BuildConfig.BASE_URL)
                .build()

            return retrofit.create(ApiService::class.java)

        }
    }

}