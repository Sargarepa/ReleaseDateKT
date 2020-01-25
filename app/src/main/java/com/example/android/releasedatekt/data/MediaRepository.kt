package com.example.android.releasedatekt.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.android.releasedatekt.database.MediaDatabase
import com.example.android.releasedatekt.database.DatabaseMovieGenreCrossRef
import com.example.android.releasedatekt.database.asDomainModelMovie
import com.example.android.releasedatekt.domain.*
import com.example.android.releasedatekt.network.MoviesGenresNetworkRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MediaRepository (private val cache: MediaDatabase) {


    fun loadMovieResults(scope: CoroutineScope): LiveData<PagedList<Movie>> {
        val dataSourceFactory = cache.mediaDao.getAllMoviesWithGenres().map {
            it.asDomainModelMovie()
        }

        val boundaryCallback = MovieBoundaryCallback(cache, scope)


        return LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()
    }

    suspend fun refreshMoviesAndGenres() {
        withContext(Dispatchers.IO) {
            val moviesAndGenres = MoviesGenresNetworkRequest.getMoviesAndGenres(1)
            for (movie in moviesAndGenres.movies) {
                for (genre in movie.genres) {
                    cache.mediaDao.insertMovieGenreCrossRef(DatabaseMovieGenreCrossRef(movie.id, genre.id))
                }
            }
            cache.mediaDao.insertAllGenres(*moviesAndGenres.genres.asDatabaseModelGenres())
            cache.mediaDao.insertAllMovies(*moviesAndGenres.movies.asDatabaseModelMovies())
        }
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 20
    }
}