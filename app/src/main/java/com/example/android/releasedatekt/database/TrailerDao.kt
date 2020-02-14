package com.example.android.releasedatekt.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TrailerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovieTrailers(vararg movieTrailers: DatabaseMovieTrailer)

    @Query("select * from databasemovietrailer where movie_id = :movieId and type = :type")
    fun getAllMovieTrailers(
        movieId: Int,
        type: String = "Trailer"
    ): LiveData<List<DatabaseMovieTrailer>>

    @Delete
    fun deleteAllMovieTrailers(vararg trailers: DatabaseMovieTrailer)
}