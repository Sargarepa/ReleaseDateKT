package com.example.android.releasedatekt.network.services

import com.example.android.releasedatekt.network.NetworkConstants.API_KEY
import com.example.android.releasedatekt.network.NetworkMovieContainer
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {
    @GET("/3/discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = API_KEY, @Query("page") page: Int = 1
    ): NetworkMovieContainer
}