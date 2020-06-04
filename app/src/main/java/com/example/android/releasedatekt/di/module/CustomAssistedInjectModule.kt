package com.example.android.releasedatekt.di.module

import com.example.android.releasedatekt.work.CustomWorkerFactory
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module

@Module(includes = [AssistedInject_CustomAssistedInjectModule::class])
@AssistedModule
interface CustomAssistedInjectModule