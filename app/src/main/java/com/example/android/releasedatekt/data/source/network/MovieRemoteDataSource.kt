package com.example.android.releasedatekt.data.source.network

import com.example.android.releasedatekt.domain.MoviesAndGenresWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import com.example.android.releasedatekt.data.Result
import java.lang.Exception

import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(private val tmDbService: TMDbService) {

    suspend fun getMoviesAndGenres(page: Int): Result<MoviesAndGenresWrapper> {
        try {
           return withContext(Dispatchers.IO) {
                val networkMoviesAsync = async { tmDbService.getMovies(page = page) }
                val networkGenresAsync = async { tmDbService.getGenres() }

                val networkMoviesResponse = networkMoviesAsync.await()
                val networkGenresResponse = networkGenresAsync.await()

                if (networkMoviesResponse.isSuccessful && networkGenresResponse.isSuccessful) {
                    val networkMoviesBody = networkMoviesResponse.body()
                    val networkGenresBody = networkGenresResponse.body()
                    if (networkMoviesBody != null && networkGenresBody != null) {
                        val domainGenres = networkGenresBody.asDomainModelGenres()
                        val domainMovies = networkMoviesBody.asDomainModelMovies(domainGenres)
                        Result.success(MoviesAndGenresWrapper(domainMovies, domainGenres))
                    } else {
                        Result.error("Results were empty")
                    }
                }
                else if (!networkMoviesResponse.isSuccessful) {
                    Result.error(" ${networkMoviesResponse.code()} ${networkMoviesResponse.message()}")
                } else {
                    Result.error(" ${networkGenresResponse.code()} ${networkGenresResponse.message()}")
                }
            }
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

}
