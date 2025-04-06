package com.jadesoft.javhub.data.repository

import com.jadesoft.javhub.data.api.ApiService
import com.jadesoft.javhub.data.db.dao.JavHubDao
import com.jadesoft.javhub.data.db.dto.HistoryEntity
import com.jadesoft.javhub.data.db.dto.MovieEntity
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.data.model.MovieDetail
import com.jadesoft.javhub.util.HtmlParser
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val service: ApiService,
    private val dao: JavHubDao,
) {
    suspend fun getMovieDetail(code: String): MovieDetail {
        return try {
            val response = service.getMovie(code)
            if (response.isSuccessful) {
                val html = response.body() ?: throw Exception("Empty response body")
                val movie = HtmlParser.parseMovieDetail(html)
                movie
            } else {
                println("HTTP Error: ${response.code()}->${response.body()}")
                MovieDetail(
                    code = "",
                    title = "",
                    bigCover = "",
                    publishDate = "",
                    duration = ""
                )
            }
        } catch (e: Exception) {
            println("HTTP Error: $e")
            MovieDetail(
                code = "",
                title = "",
                bigCover = "",
                publishDate = "",
                duration = ""
            )
        }
    }

    suspend fun AddToLibrary(movie: MovieEntity) {
        dao.insertMovie(movie)
    }

    suspend fun DeleteFromLibrary(code: String) {
        dao.deleteMovieByCode(code)
    }

    suspend fun isMovieExists(code: String): Boolean {
        return dao.isMovieExists(code)
    }

    suspend fun AddToHistory(movie: HistoryEntity) {
        dao.insertHistory(movie)
    }
}