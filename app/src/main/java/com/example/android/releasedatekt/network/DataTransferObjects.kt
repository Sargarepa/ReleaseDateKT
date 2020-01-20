package com.example.android.releasedatekt.network

import com.example.android.releasedatekt.database.DatabaseGenre
import com.example.android.releasedatekt.database.DatabaseMovie
import com.example.android.releasedatekt.domain.Genre
import com.example.android.releasedatekt.domain.Movie
import com.example.android.releasedatekt.util.genreIdsToGenres
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class NetworkMovieContainer(
    @Json(name = "page") val page: Int,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "results") val results: List<NetworkMovie>
)

@JsonClass(generateAdapter = true)
data class NetworkGenreContainer(
    @Json(name = "genres") val genres: List<NetworkGenre>
)

@JsonClass(generateAdapter = true)
data class NetworkMovie(
    @Json(name = "id") val id: Int,
    @Json(name = "popularity")val popularity: Float,
    @Json(name = "vote_count") val voteCount: Int,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "original_language") val language: String,
    @Json(name = "title") val title: String,
    @Json(name = "genre_ids") val genreIds: List<Int>,
    @Json(name = "vote_average") val voteAverage: Float,
    @Json(name = "overview") val overview: String,
    @Json(name = "release_date") val releaseDate: Date
)

@JsonClass(generateAdapter = true)
data class NetworkGenre(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String
)

fun NetworkMovieContainer.asDomainModelMovies(allGenres: List<Genre>): List<Movie> {
    return results.map {
        Movie(
            id = it.id,
            popularity = it.popularity,
            voteCount = it.voteCount,
            posterPath = it.posterPath,
            language = it.language,
            title = it.title,
            genres = genreIdsToGenres(it.genreIds, allGenres),
            voteAverage = it.voteAverage,
            overview = it.overview,
            releaseDate = it.releaseDate
        )
    }
}


fun NetworkMovieContainer.asDatabaseModelMovies(): Array<DatabaseMovie> {
    return results.map {
        DatabaseMovie(
            id = it.id,
            popularity = it.popularity,
            voteCount = it.voteCount,
            posterPath = it.posterPath,
            language = it.language,
            title = it.title,
            voteAverage = it.voteAverage,
            overview = it.overview,
            releaseDate = it.releaseDate.time
        )
    }.toTypedArray()
}

fun NetworkGenreContainer.asDomainModelGenres(): List<Genre> {
    return genres.map {
        Genre(
            id = it.id,
            name = it.name
        )
    }
}

fun NetworkGenreContainer.asDatabaseModelGenres(): Array<DatabaseGenre> {
    return genres.map {
        DatabaseGenre(
            id = it.id,
            name = it.name
        )
    }.toTypedArray()
}