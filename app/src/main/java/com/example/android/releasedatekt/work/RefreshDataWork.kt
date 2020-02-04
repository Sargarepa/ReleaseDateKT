package com.example.android.releasedatekt.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.releasedatekt.data.MediaRepository
import retrofit2.HttpException
import javax.inject.Inject

class RefreshDataWorker @Inject constructor (appContext: Context, params: WorkerParameters, private val repository: MediaRepository) : CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Payload {
        return try {
            repository.refreshMoviesAndGenres(1)
            Payload(Result.SUCCESS)
        } catch (e: HttpException) {
            Payload(Result.RETRY)
        }
    }
}