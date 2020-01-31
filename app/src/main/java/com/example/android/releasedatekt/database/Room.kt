package com.example.android.releasedatekt.database

import android.content.Context
import androidx.room.*
import com.example.android.releasedatekt.util.Factory
import com.example.android.releasedatekt.util.SingletonFactory



@Database(entities = arrayOf(DatabaseMovie::class, DatabaseGenre::class, DatabaseMovieGenreCrossRef::class), version = 9, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MediaDatabase : RoomDatabase() {
    abstract val mediaDao: MediaDao
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

fun mediaDatabaseFactory(context: Context): Factory<MediaDatabase> = SingletonFactory { getDatabase(context)}