package com.example.android.releasedatekt.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.releasedatekt.domain.Genre
import com.example.android.releasedatekt.domain.Movie
import java.util.*

@Entity
data class DatabaseMovie constructor(
    @PrimaryKey
    val id: Int,
    val popularity: Float,
    val voteCount: Int,
    val posterPath: String,
    val language: String,
    val title: String,
    val genreIds: List<Int>,
    val voteAverage: Float,
    val overview: String,
    val releaseDate: Long
)

@Entity
data class DatabaseGenre constructor(
    @PrimaryKey
    val id: Int,
    val name: String
)

fun List<DatabaseMovie>.asDomainModelMovies(): List<Movie> {
    return map {
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
            releaseDate = Date(it.releaseDate)
        )
    }
}

fun List<DatabaseGenre>.asDomainModelGenres(): List<Genre> {
    return map {
        Genre(
            id = it.id,
            name = it.name
        )
    }
}