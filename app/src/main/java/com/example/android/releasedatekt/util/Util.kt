package com.example.android.releasedatekt.util

import com.example.android.releasedatekt.domain.Genre
import java.text.SimpleDateFormat
import java.util.*

private val PUNCTUATION = listOf(", ", "; ", ": ", " ")

/**
 * Truncate long text with a preference for word boundaries and without trailing punctuation.
 */
fun String.smartTruncate(length: Int): String {
    val words = split(" ")
    var added = 0
    var hasMore = false
    val builder = StringBuilder()
    for (word in words) {
        if (builder.length > length) {
            hasMore = true
            break
        }
        builder.append(word)
        builder.append(" ")
        added += 1
    }

    PUNCTUATION.map {
        if (builder.endsWith(it)) {
            builder.replace(builder.length - it.length, builder.length, "")
        }
    }

    if (hasMore) {
        builder.append("...")
    }
    return builder.toString()
}

fun genreIdsToGenres(genreIds: List<Int>, allGenres: List<Genre>?): List<Genre> {
    val genres: MutableList<Genre> = mutableListOf()
    allGenres?.let {
        for (genreId in genreIds) {
            for (genre in allGenres) {
                if (genreId == genre.id) {
                    genres.add(genre)
                }
            }
        }
    }
    return genres
}

fun genresToGenreIds(genres: List<Genre>): List<Int> {
    val genreIds: MutableList<Int> = mutableListOf()
    for (genre in genres) {
        genreIds.add(genre.id)
    }
    return genreIds
}

object DateUtil {
    const val SERVER_FORMAT = "yyyy-MM-dd"

    val dateFormat = SimpleDateFormat(SERVER_FORMAT, Locale.US)
}