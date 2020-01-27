package com.example.android.releasedatekt.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.releasedatekt.application
import com.example.android.releasedatekt.database.getDatabase
import com.example.android.releasedatekt.data.MediaRepository
import com.example.android.releasedatekt.data.mediaRepositoryFactory
import com.example.android.releasedatekt.database.mediaDatabaseFactory
import com.example.android.releasedatekt.network.moviesGenresNetworkRequestFactory
import retrofit2.HttpException

class RefreshDataWorker (appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Payload {
        val repository = applicationContext.application.applicationComponent.mediaRepositoryFactory.get()
        return try {
            repository.refreshMoviesAndGenres(1)
            Payload(Result.SUCCESS)
        } catch (e: HttpException) {
            Payload(Result.RETRY)
        }
    }
}