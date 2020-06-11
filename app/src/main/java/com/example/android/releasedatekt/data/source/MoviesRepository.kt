package com.example.android.releasedatekt.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.android.releasedatekt.domain.Movie
import kotlinx.coroutines.CoroutineScope

interface MoviesRepository {

    fun observePagedMovies(connectivityAvailable: Boolean, coroutineScope: CoroutineScope): LiveData<PagedList<Movie>>?

    fun observeLocalPagedMovies(): LiveData<PagedList<Movie>>?

    fun observeRemotePagedMovies(ioCoroutineScope: CoroutineScope): LiveData<PagedList<Movie>>?

    fun observeMovie(id: Int): LiveData<Movie>

    suspend fun refreshMovies(page: Int)
}