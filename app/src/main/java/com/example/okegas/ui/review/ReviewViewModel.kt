package com.example.okegas.ui.review

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.okegas.model.MovieRepository
import com.example.okegas.model.local.entity.Review

class ReviewViewModel(private val repository: MovieRepository) : ViewModel() {
    fun loadMovieReview(intent: Intent, callback: (List<Review>) -> Unit) {
        repository.getMovieReviews(intent, callback)
    }
}