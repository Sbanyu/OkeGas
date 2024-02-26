package com.example.okegas.model.remote

import android.content.Intent
import android.os.Build
import androidx.lifecycle.MutableLiveData
import com.example.okegas.model.local.entity.Movie
import com.example.okegas.model.local.entity.MovieVideos
import com.example.okegas.model.local.entity.Review
import com.example.okegas.model.remote.response.MovieResponse
import com.example.okegas.model.remote.response.MovieVidResponse
import com.example.okegas.model.remote.response.ReviewResponse
import com.example.okegas.model.remote.retrofit.MovieInterface
import com.example.okegas.model.remote.retrofit.MovieService
import com.example.okegas.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
    val errorMsg = MutableLiveData<String>()

    fun getPopularMovie(callback: (List<Movie>) -> Unit) {
        val apiService = MovieService.getInstance().create(MovieInterface::class.java)
        apiService.getPopular()
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    return callback(response.body()!!.movies)
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    errorMsg.postValue(t.message)
                }
            })
    }

    fun getTopRatedMovie(callback: (List<Movie>) -> Unit) {
        val apiService = MovieService.getInstance().create(MovieInterface::class.java)
        apiService.getTopRated().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                return callback(response.body()!!.movies)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                errorMsg.postValue(t.message)
            }

        })

    }

    fun getNowPlayingMovie(callback: (List<Movie>) -> Unit) {
        val apiService = MovieService.getInstance().create(MovieInterface::class.java)
        apiService.getNowPlaying().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                return callback(response.body()!!.movies)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                errorMsg.postValue(t.message)
            }

        })
    }

    fun getMovieReview(intent: Intent, callback: (List<Review>) -> Unit) {
        val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(DetailActivity.EXTRA_DETAILS, Movie::class.java)
        } else {
            intent.getParcelableExtra<Movie>(DetailActivity.EXTRA_DETAILS) as Movie
        }

        val apiService = MovieService.getInstance().create(MovieInterface::class.java)
        apiService.getReview(movie!!.id).enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(
                call: Call<ReviewResponse>,
                response: Response<ReviewResponse>
            ) {
                return callback(response.body()!!.reviews)
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                errorMsg.postValue(t.message)
            }
        })
    }

    fun getMovieVideos(movieId: Movie, callback: (List<MovieVideos>) -> Unit) {
        val apiService = MovieService.getInstance().create(MovieInterface::class.java)
        apiService.getMovieVideos(movieId!!.id).enqueue(object : Callback<MovieVidResponse> {
            override fun onResponse(
                call: Call<MovieVidResponse>,
                response: Response<MovieVidResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback(it.movieVideos)
                    }
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<MovieVidResponse>, t: Throwable) {
                errorMsg.postValue(t.message)
            }
        })
    }


    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource = instance ?: synchronized(this) {
            instance ?: RemoteDataSource()
        }
    }
}