package com.example.android.releasedatekt.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

interface ChildWorkerFactory {
    fun create(appContext: Context, workerParameters: WorkerParameters): CoroutineWorker
}