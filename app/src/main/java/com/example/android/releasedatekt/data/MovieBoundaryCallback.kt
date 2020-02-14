package com.example.android.releasedatekt.data

import androidx.paging.PagedList
import com.example.android.releasedatekt.domain.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MovieBoundaryCallback(
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