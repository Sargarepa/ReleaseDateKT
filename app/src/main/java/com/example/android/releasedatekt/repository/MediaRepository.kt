package com.example.android.releasedatekt.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.releasedatekt.database.MediaDatabase
import com.example.android.releasedatekt.database.asDomainModelGenres
import com.example.android.releasedatekt.database.asDomainModelMovies
import com.example.android.releasedatekt.domain.Genre
import com.example.android.releasedatekt.domain.Movie
import com.example.android.releasedatekt.network.Network
import com.example.android.releasedatekt.network.NetworkConstants.API_KEY
import com.example.android.releasedatekt.network.asDatabaseModelGenres
import com.example.android.releasedatekt.network.asDatabaseModelMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MediaRepository (private val database: MediaDatabase) {

    val movies: LiveData<List<Movie>> = Transformations.map(database.mediaDao.getAllMovies()) {
        it.asDomainModelMovies()
    }

    val genres: LiveData<List<Genre>> = Transformations.map(database.mediaDao.getAllGenres()) {
        it.asDomainModelGenres()
    }

    suspend fun refreshMovies() {
        withContext(Dispatchers.IO) {
            val playlist = Network.movies.getMovies(API_KEY).await()
            database.mediaDao.insertAllMovies(*playlist.asDatabaseModelMovies())
        }
    }

    suspend fun refreshGenres() {
        withContext(Dispatchers.IO) {
            val genres = Network.genres.getGenres(API_KEY).await()
            database.mediaDao.insertAllGenres(*genres.asDatabaseModelGenres())
        }
    }
}