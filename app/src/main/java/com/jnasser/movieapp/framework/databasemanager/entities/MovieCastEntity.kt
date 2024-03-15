package com.jnasser.movieapp.framework.databasemanager.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cast_table")
data class MovieCastEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: String,
    val name: String
)