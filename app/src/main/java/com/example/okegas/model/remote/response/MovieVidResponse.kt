package com.example.okegas.model.remote.response

import com.example.okegas.model.local.entity.MovieVideos
import com.example.okegas.model.local.entity.Review
import com.google.gson.annotations.SerializedName

data class MovieVidResponse(
    @SerializedName("results")
    val movieVideos: List<MovieVideos>
)
