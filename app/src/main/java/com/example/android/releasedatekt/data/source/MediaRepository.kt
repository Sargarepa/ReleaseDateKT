package com.example.android.releasedatekt.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.android.releasedatekt.data.source.database.DatabaseMovieGenreCrossRef
import com.example.android.releasedatekt.data.source.database.MovieDao
import com.example.android.releasedatekt.data.source.database.asDomainModelMovie
import com.example.android.releasedatekt.domain.*
import com.example.android.releasedatekt.data.source.network.MoviesGenresNetworkRequest
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

    fun getMovies(scope: CoroutineScope): LiveData<PagedList<Movie>> {
        val dataSourceFactory = movieDao.getAllMoviesWithGenres().map {
            it.asDomainModelMovie()
        }

        val boundaryCallback =
            MovieBoundaryCallback(
                scope,
                ::refreshMovies
            )


        return LivePagedListBuilder(dataSourceFactory,
            DATABASE_PAGE_SIZE
        )
            .setBoundaryCallback(boundaryCallback)
            .build()
    }

    suspend fun refreshMovies(page: Int) {
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
