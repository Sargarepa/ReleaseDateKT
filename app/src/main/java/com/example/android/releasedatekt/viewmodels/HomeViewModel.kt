package com.example.android.releasedatekt.viewmodels

import androidx.lifecycle.ViewModel
import com.example.android.releasedatekt.data.source.MediaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val mediaRepository: MediaRepository) :
    ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        viewModelScope.launch {
            mediaRepository.refreshMovies(1)
        }
    }

    val movies = mediaRepository.getMovies(viewModelScope)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
