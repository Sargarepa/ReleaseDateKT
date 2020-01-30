package com.example.android.releasedatekt.di.module

import com.example.android.releasedatekt.domain.Genre
import com.example.android.releasedatekt.network.DateAdapter
import com.example.android.releasedatekt.network.NetworkConstants.BASE_URL
import com.example.android.releasedatekt.network.services.GenresService
import com.example.android.releasedatekt.network.services.MoviesService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(DateAdapter())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun provideMoviesService(retrofit: Retrofit): MoviesService {
        return retrofit.create(MoviesService::class.java)
    }

    @Singleton
    @Provides
    fun provideGenresService(retrofit: Retrofit): GenresService {
        return retrofit.create(GenresService::class.java)
    }
}