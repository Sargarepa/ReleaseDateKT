package com.example.android.releasedatekt.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object NetworkConstants {
    const val BASE_URL = "https://api.themoviedb.org/"
    const val API_KEY = "459e450632dfc0e39bfcc76eff02e6c4"
}

interface MoviesService {
    @GET("/3/discover/movie")
    fun getMovies(@Query("api_key") apiKey: String)
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(DateAdapter)
    .build()
