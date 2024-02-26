package com.example.okegas.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.okegas.di.Injection
import com.example.okegas.model.MovieRepository
import com.example.okegas.ui.detail.DetailViewModel
import com.example.okegas.ui.detail.MovieVideoViewModel
import com.example.okegas.ui.favorite.FavoriteViewModel
import com.example.okegas.ui.home.HomeViewModel
import com.example.okegas.ui.review.ReviewViewModel

class ViewModelFactory constructor(private val repository: MovieRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(this.repository) as T
        } else if ((modelClass.isAssignableFrom(ReviewViewModel::class.java))) {
            ReviewViewModel(this.repository) as T
        } else if ((modelClass.isAssignableFrom(DetailViewModel::class.java))) {
            DetailViewModel(this.repository) as T
        } else if ((modelClass.isAssignableFrom(FavoriteViewModel::class.java))) {
            FavoriteViewModel(this.repository) as T
        } else if ((modelClass.isAssignableFrom(MovieVideoViewModel::class.java))) {
            MovieVideoViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(Injection.provideRepository(context))
        }
    }
}