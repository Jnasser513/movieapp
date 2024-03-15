package com.jnasser.movieapp.framework.databasemanager

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jnasser.movieapp.domain.response.movie.MovieLanguage
import com.jnasser.movieapp.framework.databasemanager.entities.MovieCastEntity
import com.jnasser.movieapp.framework.databasemanager.entities.MovieGenreEntity
import com.jnasser.movieapp.framework.databasemanager.entities.MovieLanguageEntity

class TypeConverter {

    private val gson = Gson()

    @androidx.room.TypeConverter
    fun fromString(value: String): List<MovieGenreEntity> {
        val listType = object : TypeToken<List<MovieGenreEntity>>() {}.type
        return gson.fromJson(value, listType)
    }

    @androidx.room.TypeConverter
    fun fromList(list: List<MovieGenreEntity>): String {
        return gson.toJson(list)
    }

    @androidx.room.TypeConverter
    fun fromStringLanguage(value: String): List<MovieLanguageEntity> {
        val listType = object : TypeToken<List<MovieLanguageEntity>>() {}.type
        return gson.fromJson(value, listType)
    }

    @androidx.room.TypeConverter
    fun fromListLanguage(list: List<MovieLanguageEntity>): String {
        return gson.toJson(list)
    }

    @androidx.room.TypeConverter
    fun fromStringCast(value: String): List<MovieCastEntity> {
        val listType = object : TypeToken<List<MovieCastEntity>>() {}.type
        return gson.fromJson(value, listType)
    }

    @androidx.room.TypeConverter
    fun fromListCast(list: List<MovieCastEntity>): String {
        return gson.toJson(list)
    }

}