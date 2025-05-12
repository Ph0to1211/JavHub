package com.jadesoft.javhub.data.repository

import com.jadesoft.javhub.data.db.dao.JavHubDao
import com.jadesoft.javhub.data.db.dto.ActressEntity
import javax.inject.Inject

class FollowRepository @Inject constructor(
    private val dao: JavHubDao
) {
    suspend fun getFollowing(): List<ActressEntity> = dao.getActress()

    suspend fun deleteAllFollowing() = dao.deleteAllActress()

    suspend fun deleteFollowing(code: String) = dao.deleteActressByCode(code)
}