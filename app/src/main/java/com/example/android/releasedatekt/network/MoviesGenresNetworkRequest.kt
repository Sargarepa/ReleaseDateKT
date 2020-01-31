package com.example.android.releasedatekt.network

import com.example.android.releasedatekt.domain.MoviesAndGenresWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesGenresNetworkRequest @Inject constructor (private val remoteDataSource: RemoteDataSource) {

    suspend fun getMoviesAndGenres(page: Int): MoviesAndGenresWrapper {
        return withContext(Dispatchers.IO) {
            val networkMoviesAsync = async { remoteDataSource.fetchMovies(page = page) }
            val networkGenresAsync = async { remoteDataSource.fetchGenres() }

            val networkMovies = networkMoviesAsync.await()
            val networkGenres = networkGenresAsync.await()

            val domainGenres = networkGenres.asDomainModelGenres()
            val domainMovies = networkMovies.asDomainModelMovies(domainGenres)

            MoviesAndGenresWrapper(domainMovies, domainGenres)
        }
    }

}
