package com.example.android.releasedatekt.di.module

import android.content.Context
import com.example.android.releasedatekt.database.getDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(context: Context) = getDatabase(context)

}