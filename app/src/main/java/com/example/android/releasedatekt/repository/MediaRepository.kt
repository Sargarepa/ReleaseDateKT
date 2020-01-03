package com.example.android.releasedatekt.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.releasedatekt.database.MediaDatabase
import com.example.android.releasedatekt.database.asDomainModel
import com.example.android.releasedatekt.domain.Movie
import com.example.android.releasedatekt.network.Network
import com.example.android.releasedatekt.network.NetworkConstants.API_KEY
import com.example.android.releasedatekt.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MediaRepository (private val database: MediaDatabase) {

    val movies: LiveData<List<Movie>> = Transformations.map(database.mediaDao.getAllMovies()) {
            it.asDomainModel()
        }

    suspend fun refreshMovies() {
        withContext(Dispatchers.IO) {
            val playlist = Network.movies.getMovies(API_KEY).await()
            database.mediaDao.insertAll(*playlist.asDatabaseModel())
        }
    }
}