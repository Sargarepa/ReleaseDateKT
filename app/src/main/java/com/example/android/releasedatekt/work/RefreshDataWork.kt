package com.example.android.releasedatekt.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.releasedatekt.data.MediaRepository
import com.example.android.releasedatekt.database.MediaDao
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Provider

class RefreshDataWorker(
    appContext: Context,
    params: WorkerParameters,
    private val repository: MediaRepository,
    private val mediaDao: MediaDao
) : CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Payload {
        return try {
            mediaDao.deleteAllMovies()
            mediaDao.deleteAllGenres()
            repository.refreshMoviesAndGenres(1)
            Payload(Result.SUCCESS)
        } catch (e: HttpException) {
            Payload(Result.RETRY)
        }
    }

    class Factory @Inject constructor(
        private val repository: Provider<MediaRepository>,
        private val mediaDao: Provider<MediaDao>
    ) : ChildWorkerFactory {
        override fun create(
            appContext: Context,
            workerParameters: WorkerParameters
        ): CoroutineWorker {
            return RefreshDataWorker(
                appContext,
                workerParameters,
                repository.get(),
                mediaDao.get()
            )
        }

    }
}