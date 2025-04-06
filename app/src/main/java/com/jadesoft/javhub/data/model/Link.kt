package com.jadesoft.javhub.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class Link(
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String
)