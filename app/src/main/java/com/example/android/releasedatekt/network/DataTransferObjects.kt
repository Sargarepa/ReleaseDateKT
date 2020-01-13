package com.example.android.releasedatekt.network

import com.example.android.releasedatekt.database.DatabaseMovie
import com.example.android.releasedatekt.domain.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class NetworkMovieContainer(val results: List<NetworkMovie>)

@JsonClass(generateAdapter = true)
data class NetworkMovie(
    val id: Int,
    val popularity: Float,
    @Json(name = "vote_count") val voteCount: Int,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "original_language") val language: String,
    val title: String,
    @Json(name = "genre_ids") val genreIds: List<Int>,
    @Json(name = "vote_average") val voteAverage: Float,
    val overview: String,
    @Json(name = "release_date") val releaseDate: Date
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
            releaseDate = it.releaseDate.time
        )
    }.toTypedArray()
}