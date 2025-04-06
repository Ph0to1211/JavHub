package com.jadesoft.javhub.data.model

sealed class History {
    data class DateItem(val date: String) : History()

    data class MovieItem(
        val movie: Movie,
        val time: String // 具体时间（如 "14:30"）
    ) : History()
}