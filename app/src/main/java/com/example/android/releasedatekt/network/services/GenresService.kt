package com.example.android.releasedatekt.network.services

import com.example.android.releasedatekt.network.NetworkConstants.API_KEY
import com.example.android.releasedatekt.network.NetworkGenreContainer
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresService {
    @GET("/3/genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") api_key: String = API_KEY
    ): NetworkGenreContainer
}