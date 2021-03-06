package com.example.android.releasedatekt

import android.content.Context
import android.os.Build
import androidx.multidex.MultiDexApplication
import androidx.work.*
import com.example.android.releasedatekt.di.component.AppComponent
import com.example.android.releasedatekt.di.component.DaggerAppComponent
import com.example.android.releasedatekt.work.CustomWorkerFactory
import com.example.android.releasedatekt.work.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ReleaseDateKTApplication : MultiDexApplication() {


    private val applicationScope = CoroutineScope(Dispatchers.Default)

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .apply {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        val workerFactory: CustomWorkerFactory = appComponent.workerFactory()

        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(workerFactory).build())

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }
}

val Context.application
    get() = applicationContext as ReleaseDateKTApplication