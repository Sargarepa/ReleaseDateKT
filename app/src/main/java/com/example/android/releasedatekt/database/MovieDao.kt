package com.example.android.releasedatekt.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

@Dao
interface MovieDao {
    @Query("select * from databasemovie")
    fun getAllMovies(): LiveData<List<DatabaseMovie>>

    @Query("select * from databasegenre")
    fun getAllGenres(): LiveData<List<DatabaseGenre>>

    @Transaction
    @Query("select * from databasemovie order by page asc")
    fun getAllMoviesWithGenres(): DataSource.Factory<Int, DatabaseMovieWithGenres>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieGenreCrossRef(movieGenreCrossRef: DatabaseMovieGenreCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(vararg movies: DatabaseMovie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllGenres(vararg genres: DatabaseGenre)

    @Delete
    fun deleteAllMovies(vararg movies: DatabaseMovie)

    @Delete
    fun deleteAllGenres(vararg genres: DatabaseGenre)
}