package com.example.okegas.model.remote.retrofit

import com.example.okegas.model.remote.response.MovieResponse
import com.example.okegas.model.remote.response.MovieVidResponse
import com.example.okegas.model.remote.response.ReviewResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieInterface {
    @GET("/3/movie/popular?api_key=8cfc3dab986cec364c40f23d9de18456")
    fun getPopular(): Call<MovieResponse>

    @GET("/3/movie/top_rated?api_key=8cfc3dab986cec364c40f23d9de18456")
    fun getTopRated(): Call<MovieResponse>

    @GET("/3/movie/now_playing?api_key=8cfc3dab986cec364c40f23d9de18456")
    fun getNowPlaying(): Call<MovieResponse>

    @GET("/3/movie/{id}/reviews?api_key=8cfc3dab986cec364c40f23d9de18456")
    fun getReview(@Path("id") id: String): Call<ReviewResponse>

    @GET("/3/movie/{id}/videos?api_key=8cfc3dab986cec364c40f23d9de18456")
    fun getMovieVideos(@Path("id") id: String): Call<MovieVidResponse>


}