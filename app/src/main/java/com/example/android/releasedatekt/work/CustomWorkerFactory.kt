package com.example.android.releasedatekt.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Provider

class CustomWorkerFactory @Inject constructor(
    private val workerFactories: Map<Class<out CoroutineWorker>, @JvmSuppressWildcards Provider<ChildWorkerFactory>>
): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        val factoryProvider = workerFactories[Class.forName(workerClassName)] ?: workerFactories.asIterable().firstOrNull {
            Class.forName(workerClassName).isAssignableFrom(it.key)
        }?.value
        ?: throw IllegalArgumentException("unknown worker class $workerClassName")
        return factoryProvider.get().create(appContext, workerParameters)
    }
}