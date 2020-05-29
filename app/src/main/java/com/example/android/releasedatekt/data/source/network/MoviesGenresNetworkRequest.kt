package com.example.android.releasedatekt.data.source.network

import com.example.android.releasedatekt.domain.MoviesAndGenresWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesGenresNetworkRequest @Inject constructor(private val tmDbService: TMDbService) {

    suspend fun getMoviesAndGenres(page: Int): MoviesAndGenresWrapper {
        return withContext(Dispatchers.IO) {
            val networkMoviesAsync = async { tmDbService.getMovies(page = page) }
            val networkGenresAsync = async { tmDbService.getGenres() }

            val networkMovies = networkMoviesAsync.await()
            val networkGenres = networkGenresAsync.await()

            val domainGenres = networkGenres.asDomainModelGenres()
            val domainMovies = networkMovies.asDomainModelMovies(domainGenres)

            MoviesAndGenresWrapper(domainMovies, domainGenres)
        }
    }

}
