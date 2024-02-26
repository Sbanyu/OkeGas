package com.example.okegas.model.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.okegas.model.local.entity.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table")
    fun getFavoriteMovie(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieToFavorite(movie: Movie)

    @Delete
    suspend fun removeMovieFromFavorite(movie: Movie)

    @Query("SELECT count(*) FROM movie_table WHERE id = :id")
    fun checkMovieIsFavorite(id: String): String
}