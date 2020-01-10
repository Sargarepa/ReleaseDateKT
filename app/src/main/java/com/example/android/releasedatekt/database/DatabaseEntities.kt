package com.example.android.releasedatekt.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.releasedatekt.domain.Movie
import com.example.android.releasedatekt.util.DateUtil.dateFormat
import java.util.*

@Entity
data class DatabaseMovie constructor(
    @PrimaryKey
    val id: Int,
    val popularity: Int,
    val voteCount: Int,
    val posterPath: String,
    val language: String,
    val title: String,
    val genreIds: List<Int>,
    val voteAverage: Float,
    val overview: String,
    val releaseDate: Long
)

fun List<DatabaseMovie>.asDomainModel(): List<Movie> {
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