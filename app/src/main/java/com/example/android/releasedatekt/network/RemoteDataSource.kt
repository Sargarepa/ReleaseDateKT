package com.example.android.releasedatekt.network


import javax.inject.Inject





class RemoteDataSource @Inject constructor (private val service: TMDbService) {

    suspend fun fetchMovies(page: Int) = service.getMovies(page = page)

    suspend fun fetchGenres() = service.getGenres()

}

