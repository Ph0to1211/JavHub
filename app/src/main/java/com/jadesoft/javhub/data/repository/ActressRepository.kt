package com.jadesoft.javhub.data.repository

import com.jadesoft.javhub.data.api.ApiService
import com.jadesoft.javhub.data.db.dao.JavHubDao
import com.jadesoft.javhub.data.db.dto.ActressEntity
import com.jadesoft.javhub.data.model.Actress
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.util.HtmlParser
import javax.inject.Inject

class ActressRepository @Inject constructor(
    private val service: ApiService,
    private val dao: JavHubDao,
) {

    suspend fun getActress(censored: Boolean, code: String): Actress {
        val currentCensoredType = if (censored) "" else "uncensored"
        return try {
            val response = service.getActress(currentCensoredType, code, 1)
            if (response.isSuccessful) {
                val html = response.body() ?: throw Exception("Empty response body")
                val actress = HtmlParser.parseActressDetail(html, code, currentCensoredType == "")
                actress
            } else {
                println("HTTP Error: ${response.code()}->${response.body()}")
                Actress(
                    code = "",
                    name = "",
                    avatar = "",
                    censored = false
                )
            }
        } catch (e: Exception) {
            println("HTTP Error: $e")
            Actress(
                code = "",
                name = "",
                avatar = "",
                censored = false
            )
        }
    }

    suspend fun getMovies(censored: Boolean, code: String, page: Int): List<Movie> {
        val currentCensoredType = if (censored) "" else "uncensored"
        return try {
            val response = service.getActress(currentCensoredType, code, page)
            if (response.isSuccessful) {
                val html = response.body() ?: throw Exception("Empty response body")
                val movies = HtmlParser.parserMovies(html, currentCensoredType == "")
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

    suspend fun addFollow(actress: ActressEntity) {
        dao.insertActress(actress)
    }

    suspend fun removeFollow(code: String) {
        dao.deleteActressByCode(code)
    }

    suspend fun checkFollowed(code: String): Boolean {
        return dao.isActressExists(code)
    }

}