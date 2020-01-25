package com.example.android.releasedatekt

import android.content.Context
import android.os.Build
import androidx.multidex.MultiDexApplication
import androidx.work.*
import com.example.android.releasedatekt.work.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ReleaseDateKTApplication : MultiDexApplication() {

    lateinit var applicationComponent: ApplicationComponent

    val applicationScope = CoroutineScope(Dispatchers.Default)

    fun delayedInit() {
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

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    override fun onCreate() {
        super.onCreate()
        delayedInit()

        applicationComponent = ApplicationComponent(this)
    }
}

val Context.application
    get() = applicationContext as ReleaseDateKTApplication
