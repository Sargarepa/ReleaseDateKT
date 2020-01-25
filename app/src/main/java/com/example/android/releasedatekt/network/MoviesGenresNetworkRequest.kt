package com.example.android.releasedatekt.network

import com.example.android.releasedatekt.domain.MoviesAndGenresWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

object MoviesGenresNetworkRequest {

    suspend fun getMoviesAndGenres(page: Int): MoviesAndGenresWrapper {
        return withContext(Dispatchers.IO) {
            val networkMoviesAsync = async { Network.movies.getMovies(page = page) }
            val networkGenresAsync = async { Network.genres.getGenres() }

            val networkMovies = networkMoviesAsync.await()
            val networkGenres = networkGenresAsync.await()

            val domainGenres = networkGenres.asDomainModelGenres()
            val domainMovies = networkMovies.asDomainModelMovies(domainGenres)

            MoviesAndGenresWrapper(domainMovies, domainGenres)
        }
    }

}
