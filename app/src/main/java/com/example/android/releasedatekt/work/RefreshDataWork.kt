package com.example.android.releasedatekt.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.releasedatekt.data.MediaRepository
import com.example.android.releasedatekt.database.MovieDao
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Provider

class RefreshDataWorker(
    appContext: Context,
    params: WorkerParameters,
    private val repository: MediaRepository,
    private val movieDao: MovieDao
) : CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Payload {
        return try {
            movieDao.deleteAllMovies()
            movieDao.deleteAllGenres()
            repository.refreshMoviesAndGenres(1)
            Payload(Result.SUCCESS)
        } catch (e: HttpException) {
            Payload(Result.RETRY)
        }
    }

    class Factory @Inject constructor(
        private val repository: Provider<MediaRepository>,
        private val movieDao: Provider<MovieDao>
    ) : ChildWorkerFactory {
        override fun create(
            appContext: Context,
            workerParameters: WorkerParameters
        ): CoroutineWorker {
            return RefreshDataWorker(
                appContext,
                workerParameters,
                repository.get(),
                movieDao.get()
            )
        }

    }
}