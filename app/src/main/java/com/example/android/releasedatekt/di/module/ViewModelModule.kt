package com.example.android.releasedatekt.di.module

import androidx.lifecycle.ViewModel
import com.example.android.releasedatekt.di.annotation.ViewModelKey
import com.example.android.releasedatekt.viewmodels.DetailsViewModel
import com.example.android.releasedatekt.viewmodels.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailsViewModel(detailsViewModel: DetailsViewModel): ViewModel

}