package com.example.android.releasedatekt.network

import com.example.android.releasedatekt.domain.MoviesAndGenresWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object MoviesGenresNetworkRequest {

    suspend fun getMoviesAndGenres(): MoviesAndGenresWrapper {
        return withContext(Dispatchers.IO) {
            val networkMovies = Network.movies.getMovies().await()
            val networkGenres = Network.genres.getGenres().await()

            val domainGenres = networkGenres.asDomainModelGenres()
            val domainMovies = networkMovies.asDomainModelMovies(domainGenres)

            MoviesAndGenresWrapper(domainMovies, domainGenres)
        }
    }

}