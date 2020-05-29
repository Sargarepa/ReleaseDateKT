package com.example.android.releasedatekt.data.source

import androidx.lifecycle.LiveData
import com.example.android.releasedatekt.data.Result
import com.example.android.releasedatekt.domain.Movie

interface MoviesDataSource {
    fun observeMovies(): LiveData<Result<List<Movie>>>

    suspend fun getMovies(): Result<List<Movie>>

    suspend fun refreshMovies()

    fun observeMovies(movieId: String): LiveData<Result<Movie>>

    suspend fun getMovie(movieId: String): Result<Movie>

    suspend fun refreshMovie(movieId: String)

    suspend fun saveMovie(movie: Movie)

    suspend fun deleteAllMovies()

    suspend fun deleteMovie(movieId: String)
}