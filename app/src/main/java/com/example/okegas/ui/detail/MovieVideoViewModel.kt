package com.example.okegas.ui.detail

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.okegas.model.MovieRepository
import com.example.okegas.model.local.entity.Movie
import com.example.okegas.model.local.entity.MovieVideos
import com.example.okegas.model.local.entity.Review

class MovieVideoViewModel(private val repository: MovieRepository) : ViewModel() {
    fun loadMovieVideos(movieId: Movie, callback: (List<MovieVideos>) -> Unit) {
        repository.getMovieVideos(movieId, callback)
    }
}
