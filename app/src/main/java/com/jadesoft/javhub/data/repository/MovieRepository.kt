package com.jadesoft.javhub.data.repository

import com.jadesoft.javhub.data.api.ApiService
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.ui.movie.ListType
import com.jadesoft.javhub.util.HtmlParser
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val service: ApiService
) {
    suspend fun getData(code: String, listType: ListType, censoredType: Boolean, onlyShowMag: Boolean, page: Int)
    : List<Movie> {
        val currentListType = when (listType) {
            ListType.Genre -> "genre"
            ListType.Director -> "director"
            ListType.Producer -> "studio"
            ListType.Publisher -> "label"
            ListType.Series -> "series"
            ListType.Actress -> "star"
            else -> ""
        }
        val currentCensoredType = if (censoredType) "uncensored" else ""
        val cookie = if (onlyShowMag) "existmag=mag" else "existmag=all"
        return try {
            val response = service.getTypedMovies(
                type = currentCensoredType,
                genre = currentListType,
                code = code,
                page = page,
                cookie = cookie
            )
            if (response.isSuccessful) {
                val html = response.body() ?: throw Exception("Empty response body")
                val movies = HtmlParser.parserMovies(html)
                movies
            } else {
                println("HTTP Error: ${response.code()}->${response.body()}")
                listOf()
            }
        } catch (e: Exception) {
            println("HTTP Error: $e")
            listOf()
        }
    }

    suspend fun getActress(code: String) {

    }
}