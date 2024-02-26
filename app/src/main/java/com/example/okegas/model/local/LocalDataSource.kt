package com.example.okegas.model.local

import com.example.okegas.model.local.entity.Movie
import com.example.okegas.model.local.room.MovieDao

class LocalDataSource private constructor(
    private val movieDao: MovieDao
) {
    fun getFavoriteMovie() = movieDao.getFavoriteMovie()

    suspend fun addMovieToFavorite(movie: Movie) = movieDao.addMovieToFavorite(movie)

    suspend fun removeMovieFromFavorite(movie: Movie) = movieDao.removeMovieFromFavorite(movie)

    fun checkMovieIsFavorite(id: String) : String = movieDao.checkMovieIsFavorite(id)

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao)
    }
}