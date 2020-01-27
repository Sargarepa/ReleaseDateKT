package com.example.android.releasedatekt

import android.content.Context
import com.example.android.releasedatekt.data.mediaRepositoryFactory
import com.example.android.releasedatekt.database.mediaDatabaseFactory
import com.example.android.releasedatekt.network.moviesGenresNetworkRequestFactory
import com.example.android.releasedatekt.network.networkFactory
import com.example.android.releasedatekt.viewmodels.HomeViewModelFactory

class ApplicationComponent(context: Context) {

    private val mediaDatabaseFactory = mediaDatabaseFactory(context)
    private val networkFactory = networkFactory()
    private val moviesGenresNetworkRequestFactory = moviesGenresNetworkRequestFactory(networkFactory)
    val mediaRepositoryFactory = mediaRepositoryFactory(mediaDatabaseFactory, moviesGenresNetworkRequestFactory)

    val homeViewModelFactory = HomeViewModelFactory(mediaRepositoryFactory)
}