package com.jadesoft.javhub.data.model

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("mainGenre") val mainGenre : String,
    @SerializedName("subGenre") val subGenre : String,
    @SerializedName("code") val code : String,
    @SerializedName("url") val url : String
)