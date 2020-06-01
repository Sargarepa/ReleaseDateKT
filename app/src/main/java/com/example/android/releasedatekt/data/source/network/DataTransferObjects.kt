package com.example.android.releasedatekt.data.source.network

import com.example.android.releasedatekt.domain.Genre
import com.example.android.releasedatekt.domain.Movie
import com.example.android.releasedatekt.domain.MovieTrailer
import com.example.android.releasedatekt.util.genreIdsToGenres
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class NetworkMovieContainer(
    @Json(name = "page")
    val page: Int,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "results")
    val results: List<NetworkMovie>
)

@JsonClass(generateAdapter = true)
data class NetworkGenreContainer(
    @Json(name = "genres")
    val genres: List<NetworkGenre>
)

@JsonClass(generateAdapter = true)
data class NetworkMovieTrailerContainer(
    @Json(name = "id")
    val id: Int,
    @Json(name = "results")
    val results: List<NetworkMovieTrailer>
)

@JsonClass(generateAdapter = true)
data class NetworkMovie(
    @Json(name = "id")
    val id: Int,
    @Json(name = "popularity")
    val popularity: Float,
    @Json(name = "vote_count")
    val voteCount: Int,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "original_language")
    val language: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "genre_ids")
    val genreIds: List<Int>,
    @Json(name = "vote_average")
    val voteAverage: Float,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "release_date")
    val releaseDate: Date?
)

@JsonClass(generateAdapter = true)
data class NetworkGenre(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String
)

@JsonClass(generateAdapter = true)
data class NetworkMovieTrailer(
    @Json(name = "id")
    val id: Int,
    @Json(name = "key")
    val key: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "site")
    val site: String,
    @Json(name = "size")
    val size: Int,
    @Json(name = "type")
    val type: String
)

fun NetworkMovieTrailerContainer.asDomainModelMovieTrailers(): List<MovieTrailer> {
    return results.map {
        MovieTrailer(
            id = it.id,
            movieId = this.id,
            key = it.key,
            name = it.name,
            site = it.site,
            size = it.size,
            type = it.type
        )
    }
}

fun NetworkMovieContainer.asDomainModelMovies(allGenres: List<Genre>): List<Movie> {
    return results.map {
        Movie(
            id = it.id,
            popularity = it.popularity,
            voteCount = it.voteCount,
            posterPath = it.posterPath ?: it.backdropPath,
            language = it.language,
            title = it.title,
            genres = genreIdsToGenres(it.genreIds, allGenres),
            voteAverage = it.voteAverage,
            overview = it.overview,
            releaseDate = it.releaseDate
        )
    }
}


fun NetworkGenreContainer.asDomainModelGenres(): List<Genre> {
    return genres.map {
        Genre(
            id = it.id,
            name = it.name
        )
    }
}
