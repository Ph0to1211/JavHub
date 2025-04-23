package com.jadesoft.javhub.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tag (
    @SerialName("index") val index: Int,
    @SerialName("name") val name: String
)