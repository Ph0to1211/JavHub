package com.jadesoft.javhub.data.repository

import com.jadesoft.javhub.data.api.ApiService
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.util.HtmlParser
import javax.inject.Inject

class GenreRepository @Inject constructor(
    private val service: ApiService
) {
    suspend fun getData(code: String, type: Boolean, onlyShowMag: Boolean, page: Int): List<Movie> {
        val currentGenre = if (type) "uncensored" else ""
        val cookie = if (onlyShowMag) "existmag=mag" else "existmag=all"
        return try {
            val response = service.getGenre(
                type = currentGenre,
                code = code,
                page = page,
                cookie = cookie
            )
            if (response.isSuccessful) {
                val html = response.body() ?: throw Exception("Empty response body")
                val movies = HtmlParser.parserMovies(html, !type)
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
}