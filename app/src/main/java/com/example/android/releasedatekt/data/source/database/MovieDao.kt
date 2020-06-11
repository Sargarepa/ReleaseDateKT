package com.example.android.releasedatekt.data.source.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

@Dao
interface MovieDao {
    @Query("select * from databasemovie")
    fun getMovies(): LiveData<List<DatabaseMovie>>

    @Query("select * from databasegenre")
    fun getGenres(): LiveData<List<DatabaseGenre>>

    @Query("select * from databasemovie where movie_id = :id")
    fun getMovie(id: Int): LiveData<DatabaseMovieWithGenres>

    @Transaction
    @Query("select * from databasemovie order by page asc")
    fun getPagedMoviesWithGenres(): DataSource.Factory<Int, DatabaseMovieWithGenres>

    @Transaction
    @Query("select * from databasemovie order by page asc")
    fun getMoviesWithGenres(): LiveData<List<DatabaseMovieWithGenres>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieGenreCrossRef(movieGenreCrossRef: DatabaseMovieGenreCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(vararg movies: DatabaseMovie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(vararg genres: DatabaseGenre)

    @Delete
    fun deleteMovies(vararg movies: DatabaseMovie)

    @Delete
    fun deleteGenres(vararg genres: DatabaseGenre)
}