package com.jnasser.movieapp.framework.databasemanager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jnasser.movieapp.framework.databasemanager.daos.MovieDAO
import com.jnasser.movieapp.framework.databasemanager.entities.MovieCastEntity
import com.jnasser.movieapp.framework.databasemanager.entities.MovieEntity
import com.jnasser.movieapp.framework.databasemanager.entities.MovieGenreEntity
import com.jnasser.movieapp.framework.databasemanager.entities.MovieLanguageEntity

@Database(
    version = 1,
    entities = [
        MovieEntity::class,
        MovieGenreEntity::class,
        MovieLanguageEntity::class,
        MovieCastEntity::class
    ]
)
@TypeConverters(TypeConverter::class)
abstract class MovieDB : RoomDatabase() {
    abstract fun movieDao(): MovieDAO

    companion object {
        @Volatile
        private var INSTANCE: MovieDB? = null
        fun getDatabase(context: Context) = INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context,
                MovieDB::class.java,
                "movie_db"
            ).fallbackToDestructiveMigration().build()

            INSTANCE = instance
            instance
        }
    }
}