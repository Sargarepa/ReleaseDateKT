package com.example.android.releasedatekt.domain

import com.example.android.releasedatekt.database.DatabaseGenre
import com.example.android.releasedatekt.database.DatabaseMovie
import com.example.android.releasedatekt.network.NetworkConstants.IMAGE_BASE_URL
import com.example.android.releasedatekt.util.genresToGenreIds
import com.example.android.releasedatekt.util.smartTruncate
import java.util.*

data class Movie(
    val id: Int,
    val popularity: Float,
    val voteCount: Int,
    val posterPath: String?,
    val language: String,
    val title: String,
    val genres: List<Genre>,
    val voteAverage: Float,
    val overview: String,
    val releaseDate: Date?
) {
    //Truncated overview to be displayed in UI if the overview is too long
    val shortOverview: String
        get() = overview.smartTruncate(200)

    val fullImageUrl: String
        get() = IMAGE_BASE_URL + posterPath
}

data class Genre(
    val id: Int,
    val name: String
)

data class MoviesAndGenresWrapper(
    val movies: List<Movie>,
    val genres: List<Genre>
)

fun List<Movie>.asDatabaseModelMovies(): Array<DatabaseMovie> {
    return map {
        DatabaseMovie(
            id = it.id,
            popularity = it.popularity,
            voteCount = it.voteCount,
            posterPath = it.posterPath,
            language = it.language,
            title = it.title,
            voteAverage = it.voteAverage,
            overview = it.overview,
            releaseDate = it.releaseDate?.time
        )
    }.toTypedArray()
}

fun List<Genre>.asDatabaseModelGenres(): Array<DatabaseGenre> {
    return map {
        DatabaseGenre(
            id = it.id,
            name = it.name
        )
    }.toTypedArray()
}