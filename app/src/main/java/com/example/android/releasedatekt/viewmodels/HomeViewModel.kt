package com.example.android.releasedatekt.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.releasedatekt.data.MediaRepository
import com.example.android.releasedatekt.util.Factory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class HomeViewModel(private val mediaRepository: MediaRepository): ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        viewModelScope.launch {
            mediaRepository.refreshMoviesAndGenres(1)
        }
    }

    val movies = mediaRepository.loadMovieResults(viewModelScope)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

class HomeViewModelFactory(private val mediaRepositoryFactory: Factory<MediaRepository>) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(mediaRepositoryFactory.get()) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}