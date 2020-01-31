package com.example.android.releasedatekt.di.component

import com.example.android.releasedatekt.network.RemoteDataSource
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {

    fun network(): RemoteDataSource

}