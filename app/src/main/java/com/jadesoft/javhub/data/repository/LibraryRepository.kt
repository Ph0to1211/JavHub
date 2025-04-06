package com.jadesoft.javhub.data.repository

import com.jadesoft.javhub.data.db.dao.JavHubDao
import javax.inject.Inject

class LibraryRepository @Inject constructor(
    private val dao: JavHubDao
) {
    suspend fun getAllMovies() = dao.getAllMovies()
}