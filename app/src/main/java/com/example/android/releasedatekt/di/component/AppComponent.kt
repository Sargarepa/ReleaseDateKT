package com.example.android.releasedatekt.di.component

import android.content.Context
import com.example.android.releasedatekt.di.module.AppModule
import com.example.android.releasedatekt.ui.HomeFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: HomeFragment)

}