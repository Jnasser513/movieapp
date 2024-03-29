package com.jnasser.movieapp.framework.databasemanager.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jnasser.movieapp.framework.databasemanager.entities.MovieEntity

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieEntity: MovieEntity): Long

    @Query("DELETE FROM movie_table WHERE id = :movieId")
    fun deleteMovie(movieId: Int): Int

    @Query("SELECT * FROM movie_table")
    fun getMovies(): List<MovieEntity>

    @Query("SELECT COUNT(*) FROM movie_table WHERE id = :movieId")
    fun getMoviesIds(movieId: Int): Int

}