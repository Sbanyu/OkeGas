package com.example.okegas.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.okegas.model.MovieRepository
import com.example.okegas.model.local.entity.Movie

class FavoriteViewModel(private val repository: MovieRepository) : ViewModel() {
    fun getFavoriteMovieList(): LiveData<List<Movie>> = repository.getFavoriteMovie()
}