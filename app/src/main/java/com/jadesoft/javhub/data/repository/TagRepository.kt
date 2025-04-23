package com.jadesoft.javhub.data.repository

import com.jadesoft.javhub.data.db.dao.JavHubDao
import javax.inject.Inject

class TagRepository @Inject constructor(
    private val dao: JavHubDao
) {

    suspend fun replaceTag(tag: String) {
        dao.replaceTag(tag)
    }

}