package com.example.android.releasedatekt.di.module

import android.content.Context
import com.example.android.releasedatekt.database.MediaDatabase
import com.example.android.releasedatekt.database.getDatabase
import com.example.android.releasedatekt.network.TMDbService
import com.example.android.releasedatekt.network.TMDbService.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class, CoreDataModule::class, ViewModelFactoryModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        moshiConverterFactory: MoshiConverterFactory, okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideTMDbService(retrofit: Retrofit): TMDbService {
        return retrofit.create(TMDbService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(context: Context) = getDatabase(context)

    @Singleton
    @Provides
    fun provideMediaDao(db: MediaDatabase) = db.mediaDao


}