package com.example.android.releasedatekt.network.services

import com.example.android.releasedatekt.network.NetworkMovieContainer
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {
    @GET("/3/discover/movie")
    fun getMovies(@Query("api_key") apiKey: String, @Query("page") page: Int): Deferred<NetworkMovieContainer>
}