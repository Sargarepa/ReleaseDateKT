package com.example.android.releasedatekt.network

import com.example.android.releasedatekt.network.services.GenresService
import com.example.android.releasedatekt.network.services.MoviesService
import retrofit2.Retrofit
import javax.inject.Inject

object NetworkConstants {
    const val BASE_URL = "https://api.themoviedb.org/"
    const val API_KEY = "459e450632dfc0e39bfcc76eff02e6c4"
    const val IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185"
}



class Network @Inject constructor (retrofit: Retrofit) {
    val movies = retrofit.create(MoviesService::class.java)
    val genres = retrofit.create(GenresService::class.java)
}

