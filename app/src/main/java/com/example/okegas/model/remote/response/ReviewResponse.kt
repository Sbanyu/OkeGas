package com.example.okegas.model.remote.response

import com.example.okegas.model.local.entity.Review
import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("results")
    val reviews: List<Review>
)
