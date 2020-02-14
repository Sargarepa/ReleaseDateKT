package com.example.android.releasedatekt.di.module

import com.example.android.releasedatekt.di.annotation.WorkerKey
import com.example.android.releasedatekt.work.ChildWorkerFactory
import com.example.android.releasedatekt.work.RefreshDataWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(RefreshDataWorker::class)
    fun bindRefreshDataWorker(factory: RefreshDataWorker.Factory): ChildWorkerFactory

}