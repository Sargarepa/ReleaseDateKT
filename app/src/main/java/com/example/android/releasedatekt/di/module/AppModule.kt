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


@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {


    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

}