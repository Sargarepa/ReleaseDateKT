package com.example.android.releasedatekt.network

import com.example.android.releasedatekt.database.DatabaseMovie
import com.example.android.releasedatekt.domain.Movie
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class NetworkMovieContainer(val results: List<NetworkMovie>)

@JsonClass(generateAdapter = true)
data class NetworkMovie(
    val id: Int,
    val popularity: Int,
    val voteCount: Int,
    val posterPath: String,
    val language: String,
    val title: String,
    val genreIds: List<Int>,
    val voteAverage: Float,
    val overview: String,
    val releaseDate: Date
)

fun NetworkMovieContainer.asDomainModel(): List<Movie> {
    return results.map {
        Movie(
            id = it.id,
            popularity = it.popularity,
            voteCount = it.voteCount,
            posterPath = it.posterPath,
            language = it.language,
            title = it.title,
            genreIds = it.genreIds,
            voteAverage = it.voteAverage,
            overview = it.overview,
            releaseDate = it.releaseDate
        )
    }
}

fun NetworkMovieContainer.asDatabaseModel(): Array<DatabaseMovie> {
    return results.map {
        DatabaseMovie(
            id = it.id,
            popularity = it.popularity,
            voteCount = it.voteCount,
            posterPath = it.posterPath,
            language = it.language,
            title = it.title,
            genreIds = it.genreIds,
            voteAverage = it.voteAverage,
            overview = it.overview,
            releaseDate = it.releaseDate
        )
    }.toTypedArray()
}