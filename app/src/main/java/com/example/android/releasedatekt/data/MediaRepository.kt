package com.example.android.releasedatekt.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.android.releasedatekt.Factory
import com.example.android.releasedatekt.database.DatabaseMovieGenreCrossRef
import com.example.android.releasedatekt.database.MediaDatabase
import com.example.android.releasedatekt.database.asDomainModelMovie
import com.example.android.releasedatekt.domain.Movie
import com.example.android.releasedatekt.domain.asDatabaseModelGenres
import com.example.android.releasedatekt.domain.asDatabaseModelMovies
import com.example.android.releasedatekt.network.MoviesGenresNetworkRequest
import com.example.android.releasedatekt.singletonFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MediaRepository(private val database: MediaDatabase) {

    private val mediaDao = database.mediaDao

    fun loadMovieResults(scope: CoroutineScope): LiveData<PagedList<Movie>> {
        val dataSourceFactory = mediaDao.getAllMoviesWithGenres().map {
            it.asDomainModelMovie()
        }

        val boundaryCallback = MovieBoundaryCallback(database, scope)


        return LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()
    }

    suspend fun refreshMoviesAndGenres() {
        withContext(Dispatchers.IO) {
            val moviesAndGenres = MoviesGenresNetworkRequest.getMoviesAndGenres(1)
            for (movie in moviesAndGenres.movies) {
                for (genre in movie.genres) {
                    mediaDao.insertMovieGenreCrossRef(
                        DatabaseMovieGenreCrossRef(
                            movie.id,
                            genre.id
                        )
                    )
                }
            }
            mediaDao.insertAllGenres(*moviesAndGenres.genres.asDatabaseModelGenres())
            mediaDao.insertAllMovies(*moviesAndGenres.movies.asDatabaseModelMovies())
        }
    }

    companion object {

        private const val DATABASE_PAGE_SIZE = 20
    }
}

fun mediaRepositoryFactory(
    mediaDatabaseFactory: Factory<MediaDatabase>
) = singletonFactory { MediaRepository(mediaDatabaseFactory.get()) }
