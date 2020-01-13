package com.example.android.releasedatekt.domain

import com.example.android.releasedatekt.network.NetworkConstants.IMAGE_BASE_URL
import com.example.android.releasedatekt.util.smartTruncate
import java.util.*

data class Movie(
    val id: Int,
    val popularity: Float,
    val voteCount: Int,
    val posterPath: String,
    val language: String,
    val title: String,
    val genreIds: List<Int>,
    val voteAverage: Float,
    val overview: String,
    val releaseDate: Date
) {
    //Truncated overview to be displayed in UI if the overview is too long
    val shortOverview: String
        get() = overview.smartTruncate(200)

    val fullImageUrl: String
        get() = IMAGE_BASE_URL + posterPath
}