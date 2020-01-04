package com.example.android.releasedatekt.network

import com.example.android.releasedatekt.network.NetworkConstants.BASE_URL
import com.example.android.releasedatekt.network.services.MoviesService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkConstants {
    const val BASE_URL = "https://api.themoviedb.org/"
    const val API_KEY = "459e450632dfc0e39bfcc76eff02e6c4"
    const val IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185"
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(DateAdapter)
    .build()

object Network {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val movies = retrofit.create(MoviesService::class.java)
}