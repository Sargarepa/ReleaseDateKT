package com.example.android.releasedatekt.di.component

import android.content.Context
import com.example.android.releasedatekt.di.module.AppModule
import com.example.android.releasedatekt.di.module.CustomAssistedInjectModule
import com.example.android.releasedatekt.ui.DetailsFragment
import com.example.android.releasedatekt.ui.HomeFragment
import com.example.android.releasedatekt.work.CustomWorkerFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        CustomAssistedInjectModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: HomeFragment)

    fun inject(fragment: DetailsFragment)

    fun workerFactory() : CustomWorkerFactory
}