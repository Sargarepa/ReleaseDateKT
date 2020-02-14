package com.example.android.releasedatekt.di.annotation

import androidx.work.CoroutineWorker
import dagger.MapKey
import kotlin.reflect.KClass

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@MapKey
annotation class WorkerKey(val value: KClass<out CoroutineWorker>)