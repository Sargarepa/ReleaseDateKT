package com.example.android.releasedatekt.di.module

import com.example.android.releasedatekt.data.source.database.MediaDatabase
import com.example.android.releasedatekt.data.source.network.TMDbService
import com.example.android.releasedatekt.work.CustomWorkerFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(
    includes = [ViewModelModule::class,
        NetworkModule::class,
        ViewModelFactoryModule::class,
        WorkerModule::class,
        DatabaseModule::class]
)
class AppModule {

    @Singleton
    @Provides
    fun provideTMDbService(retrofit: Retrofit): TMDbService {
        return retrofit.create(TMDbService::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieDao(db: MediaDatabase) = db.movieDao

    @Singleton
    @Provides
    fun provideTrailerDao(db: MediaDatabase) = db.trailerDao
}