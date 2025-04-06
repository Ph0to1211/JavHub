package com.jadesoft.javhub.data.repository

import com.jadesoft.javhub.data.db.dao.JavHubDao
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val dao: JavHubDao
){
    suspend fun getHistory() = dao.getHistories()

    suspend fun deleteHistory(code: String) = dao.deleteHistory(code)
}