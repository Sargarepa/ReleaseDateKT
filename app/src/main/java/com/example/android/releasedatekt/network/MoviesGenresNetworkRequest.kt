package com.example.android.releasedatekt.network

import com.example.android.releasedatekt.domain.MoviesAndGenresWrapper
import com.example.android.releasedatekt.util.Factory
import com.example.android.releasedatekt.util.SingletonFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MoviesGenresNetworkRequest(private val network: Network) {

    suspend fun getMoviesAndGenres(page: Int): MoviesAndGenresWrapper {
        return withContext(Dispatchers.IO) {
            val networkMoviesAsync = async { network.movies.getMovies(page = page) }
            val networkGenresAsync = async { network.genres.getGenres() }

            val networkMovies = networkMoviesAsync.await()
            val networkGenres = networkGenresAsync.await()

            val domainGenres = networkGenres.asDomainModelGenres()
            val domainMovies = networkMovies.asDomainModelMovies(domainGenres)

            MoviesAndGenresWrapper(domainMovies, domainGenres)
        }
    }

}

fun moviesGenresNetworkRequestFactory(
    networkFactory: Factory<Network>
) : Factory<MoviesGenresNetworkRequest> = SingletonFactory { MoviesGenresNetworkRequest(networkFactory.get()) }