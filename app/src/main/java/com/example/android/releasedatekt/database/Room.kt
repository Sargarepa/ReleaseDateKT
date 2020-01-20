package com.example.android.releasedatekt.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MediaDao {
    @Query("select * from databasemovie")
    fun getAllMovies(): LiveData<List<DatabaseMovie>>

    @Query("select * from databasegenre")
    fun getAllGenres(): LiveData<List<DatabaseGenre>>

    @Transaction
    @Query("select * from databasemovie")
    fun getAllMoviesWithGenres(): LiveData<List<DatabaseMovieWithGenres>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieGenreCrossRef(movieGenreCrossRef: DatabaseMovieGenreCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(vararg movies: DatabaseMovie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllGenres(vararg genres: DatabaseGenre)
}

@Database(entities = arrayOf(DatabaseMovie::class, DatabaseGenre::class, DatabaseMovieGenreCrossRef::class), version = 5)
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