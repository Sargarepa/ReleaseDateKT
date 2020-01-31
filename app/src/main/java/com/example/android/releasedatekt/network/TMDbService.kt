package com.example.android.releasedatekt.network

import com.example.android.releasedatekt.network.NetworkConstants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDbService {
    @GET("/3/genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") api_key: String = API_KEY
    ): NetworkGenreContainer

    @GET("/3/discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = API_KEY, @Query("page") page: Int = 1
    ): NetworkMovieContainer
}