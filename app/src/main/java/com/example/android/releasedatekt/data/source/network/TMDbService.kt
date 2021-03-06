package com.example.android.releasedatekt.data.source.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDbService {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/"
        const val API_KEY = "459e450632dfc0e39bfcc76eff02e6c4"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185"
    }

    @GET("/3/genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") api_key: String = API_KEY
    ): Response<NetworkGenresContainer>

    @GET("/3/discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = API_KEY, @Query("page") page: Int = 1
    ): Response<NetworkMoviesContainer>

    @GET("/3/movie/{movie_id}")
    suspend fun getMovie(
        @Path ("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ) : Response<NetworkMovie>

    @GET("/3/movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<NetworkMovieTrailerContainer>
}