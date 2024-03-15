package com.jnasser.movieapp.framework.databasemanager.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "language_table")
data class MovieLanguageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)