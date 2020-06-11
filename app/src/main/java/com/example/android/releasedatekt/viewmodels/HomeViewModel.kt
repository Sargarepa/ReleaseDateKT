package com.example.android.releasedatekt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.releasedatekt.data.source.DefaultMoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val defaultMoviesRepository: DefaultMoviesRepository) :
    ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var connectivityAvailable: Boolean = false

    val movies = defaultMoviesRepository.observePagedMovies(connectivityAvailable, viewModelScope)

    private val _navigateToDetailsFragment = MutableLiveData<Int>()
    val navigateToDetailsFragment: LiveData<Int>
        get() = _navigateToDetailsFragment

    fun onMovieClicked(id: Int) {
        _navigateToDetailsFragment.value = id
    }

    fun onNavigateToDetailsFragmentComplete() {
        _navigateToDetailsFragment.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
