package com.example.android.releasedatekt.data

import androidx.paging.PagedList
import com.example.android.releasedatekt.database.DatabaseMovieGenreCrossRef
import com.example.android.releasedatekt.database.DatabaseMovieWithGenres
import com.example.android.releasedatekt.database.MediaDatabase
import com.example.android.releasedatekt.domain.Movie
import com.example.android.releasedatekt.domain.asDatabaseModelGenres
import com.example.android.releasedatekt.domain.asDatabaseModelMovies
import com.example.android.releasedatekt.network.MoviesGenresNetworkRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieBoundaryCallback (
    private val cache: MediaDatabase,
    private val scope: CoroutineScope,
    private val block: suspend (Int) -> Unit
) : PagedList.BoundaryCallback<Movie>() {

    private var lastRequestedPage = 1

    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        scope.launch {
            requestAndSaveData()
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: Movie) {
        scope.launch {
            requestAndSaveData()
        }
    }

    private suspend fun requestAndSaveData() {
        if (isRequestInProgress) return

        isRequestInProgress = true

        block(lastRequestedPage++)

        isRequestInProgress = false
    }
}