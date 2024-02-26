package com.example.okegas.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.okegas.model.MovieRepository
import com.example.okegas.model.local.entity.Movie
import com.example.okegas.model.local.entity.MovieVideos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MovieRepository) : ViewModel() {
    fun addMovieToFavorite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMovieToFavorite(movie)
        }
    }

    fun deleteMovieFromFavorite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeMovieFromFavorite(movie)
        }
    }

    fun checkMovieIsFavorite(id: String) = repository.checkMovieIsFavorite(id)

    fun loadMovieVideos(movieId: Movie, callback: (List<MovieVideos>) -> Unit) {
        repository.getMovieVideos(movieId, callback)
    }
}