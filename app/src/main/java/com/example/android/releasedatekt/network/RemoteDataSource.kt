package com.example.android.releasedatekt.network


import javax.inject.Inject

object NetworkConstants {
    const val BASE_URL = "https://api.themoviedb.org/"
    const val API_KEY = "459e450632dfc0e39bfcc76eff02e6c4"
    const val IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185"
}



class RemoteDataSource @Inject constructor (private val service: TMDbService) {

    suspend fun fetchMovies(page: Int) = service.getMovies(page = page)

    suspend fun fetchGenres() = service.getGenres()

}

