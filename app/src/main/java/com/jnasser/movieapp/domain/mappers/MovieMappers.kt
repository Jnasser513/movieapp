package com.jnasser.movieapp.domain.mappers

import com.jnasser.movieapp.domain.response.movie.MovieCast
import com.jnasser.movieapp.domain.response.movie.MovieGenre
import com.jnasser.movieapp.domain.response.movie.MovieLanguage
import com.jnasser.movieapp.framework.databasemanager.entities.MovieCastEntity
import com.jnasser.movieapp.framework.databasemanager.entities.MovieGenreEntity
import com.jnasser.movieapp.framework.databasemanager.entities.MovieLanguageEntity


fun List<MovieGenre>.toMovieGenreEntityList() = map(MovieGenre::toMovieGenreEntity)

fun MovieGenre.toMovieGenreEntity() = MovieGenreEntity(
    id, name
)

fun List<MovieLanguage>.toMovieLanguageEntityList() = map(MovieLanguage::toMovieLanguageEntity)

fun MovieLanguage.toMovieLanguageEntity() = MovieLanguageEntity(
    0, name
)

fun List<MovieCast>.toMovieCastEntityList() = map(MovieCast::toMovieCastEntity)

fun MovieCast.toMovieCastEntity() = MovieCastEntity(
    0, image.toString(), name.toString()
)