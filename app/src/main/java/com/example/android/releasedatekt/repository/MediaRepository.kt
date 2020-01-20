package com.example.android.releasedatekt.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.releasedatekt.database.MediaDatabase
import com.example.android.releasedatekt.database.DatabaseMovieGenreCrossRef
import com.example.android.releasedatekt.database.asDomainModelGenres
import com.example.android.releasedatekt.database.asDomainModelMovies
import com.example.android.releasedatekt.domain.*
import com.example.android.releasedatekt.network.MoviesGenresNetworkRequest
import com.example.android.releasedatekt.network.Network
import com.example.android.releasedatekt.network.asDatabaseModelGenres
import com.example.android.releasedatekt.network.asDatabaseModelMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MediaRepository (private val database: MediaDatabase) {

    val genres: LiveData<List<Genre>> = Transformations.map(database.mediaDao.getAllGenres()) {
        it.asDomainModelGenres()
    }

    val movies: LiveData<List<Movie>> = Transformations.map(database.mediaDao.getAllMoviesWithGenres()) {
        it.asDomainModelMovies()
    }

    suspend fun refreshMoviesAndGenres() {
        withContext(Dispatchers.IO) {
            val moviesAndGenres = MoviesGenresNetworkRequest.getMoviesAndGenres()
            for (movie in moviesAndGenres.movies) {
                for (genre in movie.genres) {
                    database.mediaDao.insertMovieGenreCrossRef(DatabaseMovieGenreCrossRef(movie.id, genre.id))
                }
            }
            database.mediaDao.insertAllGenres(*moviesAndGenres.genres.asDatabaseModelGenres())
            database.mediaDao.insertAllMovies(*moviesAndGenres.movies.asDatabaseModelMovies())
        }
    }

    suspend fun refreshMovies() {
        withContext(Dispatchers.IO) {
            val playlist = Network.movies.getMovies().await()
            database.mediaDao.insertAllMovies(*playlist.asDatabaseModelMovies())
        }
    }

    suspend fun refreshGenres() {
        withContext(Dispatchers.IO) {
            val genres = Network.genres.getGenres().await()
            database.mediaDao.insertAllGenres(*genres.asDatabaseModelGenres())
        }
    }
}