package com.example.android.releasedatekt.viewmodels

import androidx.lifecycle.ViewModel
import com.example.android.releasedatekt.data.source.DefaultMoviesRepository
import javax.inject.Inject
import kotlin.properties.Delegates

class DetailsViewModel @Inject constructor(private val defaultMoviesRepository: DefaultMoviesRepository) : ViewModel() {

    var movieId by Delegates.notNull<Int>()

    val movie by lazy {
        defaultMoviesRepository.observeMovie(movieId)
    }
}