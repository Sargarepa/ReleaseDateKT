package com.example.android.releasedatekt.viewmodels

import androidx.lifecycle.ViewModel
import com.example.android.releasedatekt.data.source.DefaultMoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val defaultMoviesRepository: DefaultMoviesRepository) :
    ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var connectivityAvailable: Boolean = false

    init {
        viewModelScope.launch {
            defaultMoviesRepository.refreshMovies(1)
        }
    }

    val movies = defaultMoviesRepository.observePagedMovies(connectivityAvailable, viewModelScope)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
