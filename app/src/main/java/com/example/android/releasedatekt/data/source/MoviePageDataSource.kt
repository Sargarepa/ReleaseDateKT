package com.example.android.releasedatekt.data.source

import androidx.paging.PageKeyedDataSource
import com.example.android.releasedatekt.data.source.database.MovieDao
import com.example.android.releasedatekt.data.source.network.MovieRemoteDataSource
import com.example.android.releasedatekt.domain.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviePageDataSource @Inject constructor(
    private val dataSource: MovieRemoteDataSource,
    private val dao: MovieDao,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Movie>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        fetchData(1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(page: Int, pageSize: Int, callback: (List<Movie>) -> Unit) {
        scope.launch {
            val response = dataSource.getMoviesAndGenres(page)
        }
    }
}