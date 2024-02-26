package com.example.okegas.model.local.entity

import android.os.Parcelable
import com.example.okegas.model.local.entity.AuthorDetail
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieVideos(
    @SerializedName("id")
    var movieId: String = "",

    @SerializedName("key")
    val key: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("site")
    val site: String,

    @SerializedName("type")
    val type: String
) : Parcelable