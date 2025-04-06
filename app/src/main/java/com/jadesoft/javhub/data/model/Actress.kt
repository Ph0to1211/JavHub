package com.jadesoft.javhub.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Actress(
    @SerialName("code") val code: String,
    @SerialName("name") val name: String,
    @SerialName("avatar") val avatar: String,
    @SerialName("birthday") val birthday: String = "",
    @SerialName("age") val age: String = "",
    @SerialName("height") val height: String = "",
    @SerialName("cup") val cup: String = "",
    @SerialName("bust") val bust: String = "",
    @SerialName("hip") val hip: String = "",
    @SerialName("waist") val waist: String = "",
    @SerialName("birthplace") val birthplace: String = "",
    @SerialName("hobby") val hobby: String = ""
)