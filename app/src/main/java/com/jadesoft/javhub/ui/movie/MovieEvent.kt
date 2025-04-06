package com.jadesoft.javhub.ui.movie

sealed class MovieEvent {
    data class LoadItems(
        val code: String,
        val censoredType: Boolean,
        val listType: ListType
    ) : MovieEvent()
}