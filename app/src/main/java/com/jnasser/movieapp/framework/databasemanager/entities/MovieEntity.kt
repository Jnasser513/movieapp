package com.jnasser.movieapp.framework.databasemanager.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.jnasser.movieapp.domain.response.movie.MovieCast
import com.jnasser.movieapp.domain.response.movie.MovieGenre
import com.jnasser.movieapp.domain.response.movie.MovieLanguage

@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val point: Double,
    val genres: List<MovieGenreEntity>,
    val time: Int,
    val languages: List<MovieLanguageEntity>,
    val description: String,
    val cast: List<MovieCast>
)
