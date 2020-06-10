package com.example.android.releasedatekt.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.android.releasedatekt.data.source.database.DatabaseMovieGenreCrossRef
import com.example.android.releasedatekt.data.source.database.MovieDao
import com.example.android.releasedatekt.data.source.database.asDomainModelMovie
import com.example.android.releasedatekt.domain.*
import com.example.android.releasedatekt.data.source.network.MovieRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultMoviesRepository
@Inject constructor(
    private val movieDao: MovieDao,
    private val movieRemoteDataSource: MovieRemoteDataSource
) {

    fun observePagedMovies(connectivityAvailable: Boolean, coroutineScope: CoroutineScope) =
        if (connectivityAvailable) observeRemotePagedMovies(coroutineScope)
        else observeLocalPagedMovies()


    fun observeLocalPagedMovies(): LiveData<PagedList<Movie>>? {
        val dataSourceFactory = movieDao.getPagedMoviesWithGenres().map {
            it.asDomainModelMovie()
        }
        return LivePagedListBuilder(
            dataSourceFactory,
            MoviePageDataSourceFactory.pagedListConfig()
        ).build()
    }

    fun observeRemotePagedMovies(ioCoroutineScope: CoroutineScope): LiveData<PagedList<Movie>>? {
        val dataSourceFactory = MoviePageDataSourceFactory(movieRemoteDataSource, movieDao, ioCoroutineScope)
        return LivePagedListBuilder(dataSourceFactory, MoviePageDataSourceFactory.pagedListConfig()).build()
    }

    //TODO
    fun observeMovie() = null

    fun getMovies(scope: CoroutineScope): LiveData<PagedList<Movie>> {
        val dataSourceFactory = movieDao.getPagedMoviesWithGenres().map {
            it.asDomainModelMovie()
        }

        val boundaryCallback =
            MovieBoundaryCallback(
                scope,
                ::refreshMovies
            )


        return LivePagedListBuilder(
            dataSourceFactory,
            DATABASE_PAGE_SIZE
        )
            .setBoundaryCallback(boundaryCallback)
            .build()
    }

    suspend fun refreshMovies(page: Int) {
        withContext(Dispatchers.IO) {
            val moviesAndGenres = movieRemoteDataSource.getMoviesAndGenres(page).data
            moviesAndGenres?.apply {
                for (movie in moviesAndGenres.movies) {
                    for (genre in movie.genres) {
                        movieDao.insertMovieGenreCrossRef(
                            DatabaseMovieGenreCrossRef(
                                movie.id,
                                genre.id
                            )
                        )
                    }
                }
                movieDao.insertGenres(*moviesAndGenres.genres.asDatabaseModelGenres())
                movieDao.insertMovies(*moviesAndGenres.movies.asDatabaseModelMovies(page))
            }
        }
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 20
    }
}
