package com.example.android.releasedatekt.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MediaDao {
    @Query("select * from databasemovie")
    fun getAllMovies(): LiveData<List<DatabaseMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movies: DatabaseMovie)
}

@Database(entities = [DatabaseMovie::class], version = 1)
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
            ).build()
        }
    }
    return INSTANCE
}