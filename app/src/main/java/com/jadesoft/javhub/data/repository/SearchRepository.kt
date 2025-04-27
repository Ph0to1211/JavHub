package com.jadesoft.javhub.data.repository

import com.jadesoft.javhub.data.api.ApiService
import com.jadesoft.javhub.data.db.dao.JavHubDao
import com.jadesoft.javhub.data.db.dto.SearchHistoryEntity
import com.jadesoft.javhub.data.model.Actress
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.util.HtmlParser
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val service: ApiService,
    private val dao: JavHubDao
) {
    suspend fun search(query: String, showUncensored: Boolean, onlyShowMag: Boolean, page: Int, type: Int): List<Movie> {
        val currentGenre = if (showUncensored) "uncensored" else ""
        val cookie = if (onlyShowMag) "existmag=mag" else "existmag=all"
        return try {
            val response = service.search(
                genre = currentGenre,
                query = query,
                page = page,
                type = type,
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

    suspend fun searchActress(query: String, page: Int): List<Actress> {
        return try {
            val response = service.searchActress(
                query = query,
                page = page
            )
            if (response.isSuccessful) {
                val html = response.body() ?: throw Exception("Empty response body")
                val actress = HtmlParser.parseActressSearch(html)
                actress
            } else {
                println("HTTP Error: ${response.code()}->${response.body()}")
                listOf()
            }
        } catch (e: Exception) {
            println("HTTP Error: $e")
            listOf()
        }
    }

    suspend fun getSearchHistories(): List<SearchHistoryEntity> {
        return dao.getSearchHistories()
    }

    suspend fun insertSearchHistory(searchHistory: SearchHistoryEntity) {
        dao.insertSearchHistory(searchHistory)
    }

    suspend fun deleteSingleSearchHistory(query: String) {
        dao.deleteSearchHistoryByQuery(query)
    }

    suspend fun deleteSearchHistory() {
        dao.deleteSearchHistories()
    }

    suspend fun deleteFirstSearchHistory() {
        dao.deleteFirstSearchHistory()
    }
}