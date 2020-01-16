package com.example.android.releasedatekt.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.releasedatekt.database.getDatabase
import com.example.android.releasedatekt.repository.MediaRepository
import retrofit2.HttpException

class RefreshDataWorker (appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Payload {
        val database = getDatabase(applicationContext)
        val repository = MediaRepository(database)
        return try {
            repository.refreshMovies()
            repository.refreshGenres()
            Payload(Result.SUCCESS)
        } catch (e: HttpException) {
            Payload(Result.RETRY)
        }
    }
}