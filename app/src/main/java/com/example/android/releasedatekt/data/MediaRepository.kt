package com.example.android.releasedatekt.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.android.releasedatekt.database.DatabaseMovieGenreCrossRef
import com.example.android.releasedatekt.database.MovieDao
import com.example.android.releasedatekt.database.asDomainModelMovie
import com.example.android.releasedatekt.domain.*
import com.example.android.releasedatekt.network.MoviesGenresNetworkRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaRepository
@Inject constructor(
    private val movieDao: MovieDao,
    private val moviesGenresNetworkRequest: MoviesGenresNetworkRequest
) {

    fun loadMovieResults(scope: CoroutineScope): LiveData<PagedList<Movie>> {
        val dataSourceFactory = movieDao.getAllMoviesWithGenres().map {
            it.asDomainModelMovie()
        }

        val boundaryCallback = MovieBoundaryCallback(scope, ::refreshMoviesAndGenres)


        return LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()
    }

    suspend fun refreshMoviesAndGenres(page: Int) {
        withContext(Dispatchers.IO) {
            val moviesAndGenres = moviesGenresNetworkRequest.getMoviesAndGenres(page)
            for (movie in moviesAndGenres.movies) {
                for (genre in movie.genres) {
                    movieDao.insertMovieGenreCrossRef(
                        DatabaseMovieGenreCrossRef(
                            movie.id,
                            genre.id
                        )
                    )
                }
            }
            movieDao.insertAllGenres(*moviesAndGenres.genres.asDatabaseModelGenres())
            movieDao.insertAllMovies(*moviesAndGenres.movies.asDatabaseModelMovies(page))
        }
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 20
    }
}
