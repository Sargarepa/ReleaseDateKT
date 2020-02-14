package com.example.android.releasedatekt.database

import android.content.Context
import androidx.room.*


@Database(
    entities = arrayOf(
        DatabaseMovie::class,
        DatabaseGenre::class,
        DatabaseMovieGenreCrossRef::class,
        DatabaseMovieTrailer::class
    ), version = 10, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MediaDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
    abstract val trailerDao: TrailerDao
}

private lateinit var INSTANCE: MediaDatabase

fun getDatabase(context: Context): MediaDatabase {
    synchronized(MediaDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context,
                MediaDatabase::class.java,
                "media"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}
