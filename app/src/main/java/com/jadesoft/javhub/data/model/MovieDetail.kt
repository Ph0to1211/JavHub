package com.jadesoft.javhub.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    @SerializedName("code") val code: String,
    @SerializedName("censored") val censored: Boolean,
    @SerializedName("title") val title: String,
    @SerializedName("bigCover") val bigCover: String,
    @SerializedName("publishDate") val publishDate: String,
    @SerializedName("duration") val duration: String,
    @SerializedName("director") val director: Link? = Link("", ""),
    @SerializedName("publisher") val publisher: Link? = Link("", ""),
    @SerializedName("producer") val producer: Link? = Link("", ""),
    @SerializedName("series") val series: Link? = Link("", ""),
    @SerializedName("genres") val genres: List<Link>? = emptyList(),
    @SerializedName("actress") val actress: List<Actress>? = emptyList(),
    @SerializedName("images") val images: List<String>? = emptyList(),
    @SerializedName("similarMovies") val similarMovies: List<Movie>? = emptyList()
)