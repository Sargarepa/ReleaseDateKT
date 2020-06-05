package com.example.android.releasedatekt.data.source

import androidx.paging.PageKeyedDataSource
import com.example.android.releasedatekt.data.source.database.MovieDao
import com.example.android.releasedatekt.data.source.network.MovieRemoteDataSource
import com.example.android.releasedatekt.domain.Movie
import kotlinx.coroutines.CoroutineScope
import com.example.android.releasedatekt.data.Result
import com.example.android.releasedatekt.data.source.database.DatabaseMovieGenreCrossRef
import com.example.android.releasedatekt.domain.asDatabaseModelGenres
import com.example.android.releasedatekt.domain.asDatabaseModelMovies
import kotlinx.coroutines.launch
import timber.log.Timber
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
        fetchData(1) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val page = params.key
        fetchData(page) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val page = params.key
        fetchData(page) {
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(page: Int, callback: (List<Movie>) -> Unit) {
        scope.launch {
            val response = dataSource.getMoviesAndGenres(page)
            if (response.status == Result.Status.SUCCESS) {
                response.data?.apply {
                    for (movie in response.data.movies) {
                        for (genre in movie.genres) {
                            dao.insertMovieGenreCrossRef(
                                DatabaseMovieGenreCrossRef(
                                    movie.id,
                                    genre.id
                                )
                            )
                        }
                    }
                    dao.insertGenres(*response.data.genres.asDatabaseModelGenres())
                    dao.insertMovies(*response.data.movies.asDatabaseModelMovies(page))
                }
            } else if (response.status == Result.Status.ERROR) {
                postError(response.message!!)
            }
        }
    }

    private fun postError(message: String) {
        Timber.e("An error happened: $message")
        // TODO network error handling
        //networkState.postValue(NetworkState.FAILED)
    }
}