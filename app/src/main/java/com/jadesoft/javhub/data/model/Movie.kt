package com.jadesoft.javhub.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("code") val code: String,
    @SerializedName("title") val title: String,
    @SerializedName("cover") val cover: String,
    @SerializedName("censored") val censored: Boolean,
)