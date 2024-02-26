package com.example.okegas.model

import android.content.Intent
import androidx.lifecycle.LiveData
import com.example.okegas.model.local.LocalDataSource
import com.example.okegas.model.local.entity.Movie
import com.example.okegas.model.local.entity.MovieVideos
import com.example.okegas.model.local.entity.Review
import com.example.okegas.model.remote.RemoteDataSource

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    fun getFavoriteMovie(): LiveData<List<Movie>> = localDataSource.getFavoriteMovie()

    suspend fun addMovieToFavorite(movie: Movie) {
        localDataSource.addMovieToFavorite(movie)
    }

    suspend fun removeMovieFromFavorite(movie: Movie) {
        localDataSource.removeMovieFromFavorite(movie)
    }

    fun checkMovieIsFavorite(id: String) : String = localDataSource.checkMovieIsFavorite(id)

    fun getPopularMovies(callback: (List<Movie>) -> Unit) =
        remoteDataSource.getPopularMovie(callback)

    fun getTopRatedMovies(callback: (List<Movie>) -> Unit) =
        remoteDataSource.getTopRatedMovie(callback)

    fun getNowPlayingMovies(callback: (List<Movie>) -> Unit) =
        remoteDataSource.getNowPlayingMovie(callback)

    fun getMovieReviews(intent: Intent, callback: (List<Review>) -> Unit) =
        remoteDataSource.getMovieReview(intent, callback)

    fun getMovieVideos(movieId: Movie, callback: (List<MovieVideos>) -> Unit) {
        remoteDataSource.getMovieVideos(movieId, callback)
    }

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteDataSource, localDataSource)
            }
    }
}