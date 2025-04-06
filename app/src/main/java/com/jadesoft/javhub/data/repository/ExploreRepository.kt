package com.jadesoft.javhub.data.repository

import com.jadesoft.javhub.data.api.ApiService
import com.jadesoft.javhub.data.model.Actress
import com.jadesoft.javhub.data.model.Genre
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.util.HtmlParser
import javax.inject.Inject

class ExploreRepository @Inject constructor(
    private val service: ApiService
) {
    suspend fun getAllMovie(showUncensored: Boolean, onlyShowMag: Boolean, page: Int): List<Movie> {
        val currentGenre = if (showUncensored) "uncensored" else ""
        val cookie = if (onlyShowMag) "existmag=mag" else "existmag=all"
        val cacheControl = "no-cache"
        return try {
            val response = service.getAllMovies(currentGenre, page, cookie, cacheControl)
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

    suspend fun getAllActress(showUncensored: Boolean, page: Int): List<Actress> {
        val currentGenre = if (showUncensored) "uncensored" else ""
        return try {
            val response = service.getAllActresses(currentGenre, page)
            if (response.isSuccessful) {
                val html = response.body() ?: throw Exception("Empty response body")
                val actresses = HtmlParser.parseActresses(html)
                actresses
            } else {
                println("HTTP Error: ${response.code()}->${response.body()}")
                listOf()
            }
        } catch (e: Exception) {
            println("HTTP Error: $e")
            listOf()
        }
    }

    suspend fun getFilteredMovies(code: String, type: Boolean, onlyShowMag: Boolean, page: Int): List<Movie> {
        val currentType = if (type) "uncensored" else ""
        val cookie = if (onlyShowMag) "existmag=mag" else "existmag=all"
        return try {
            val response = service.getFilteredMovie(currentType, code, page, cookie)
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

    suspend fun getGenres(showUncensored: Boolean): Map<String, List<Genre>> {
        val currentGenre = if (showUncensored) "uncensored" else ""
        return try {
            val response = service.getAllGenres(currentGenre)
            if (response.isSuccessful) {
                val html = response.body() ?: throw Exception("Empty response body")
                val genres = HtmlParser.parseGenres(html)
                genres
            } else {
                println("HTTP Error: ${response.code()}->${response.body()}")
                emptyMap()
            }
        } catch (e: Exception) {
            println("HTTP Error: $e")
            emptyMap()
        }
    }
}