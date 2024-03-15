package com.jnasser.movieapp.framework.databasemanager.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "genre_table")
data class MovieGenreEntity(
    @PrimaryKey
    val id: Int,
    val name: String
)
