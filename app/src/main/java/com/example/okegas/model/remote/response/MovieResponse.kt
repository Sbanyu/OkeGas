package com.example.okegas.model.remote.response

import com.example.okegas.model.local.entity.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results")
    val movies: List<Movie>
)
