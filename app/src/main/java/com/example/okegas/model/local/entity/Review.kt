package com.example.okegas.model.local.entity

import android.os.Parcelable
import com.example.okegas.model.local.entity.AuthorDetail
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    @SerializedName("id")
    var movieId: String = "",

    @SerializedName("author_details")
    val authorDetail: AuthorDetail,

    @SerializedName("content")
    var content: String = "",

    @SerializedName("created_at")
    val createdAt: String = ""
) : Parcelable