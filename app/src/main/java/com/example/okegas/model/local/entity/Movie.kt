package com.example.okegas.model.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movie_table")
@Parcelize
data class Movie (
    @PrimaryKey
    @SerializedName("id")
    var id: String = "",

    @SerializedName("title")
    var title: String = "",

    @SerializedName("release_date")
    var releaseDate: String = "",

    @SerializedName("poster_path")
    var posterPath: String = "",

    @SerializedName("overview")
    var overview: String = "",

    @SerializedName("backdrop_path")
    var backdrop: String = "",

    @SerializedName("vote_average")
    var voteAverage: Double = 0.0,

    @SerializedName("vote_count")
    var voteCount: Int = 0
) : Parcelable {
    constructor() : this("", "", "", "", "", "", 0.0, 0)
}