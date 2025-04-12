package com.jadesoft.javhub.data.db.dto

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["query"], unique = true)]
)
data class SearchHistoryEntity(
    @PrimaryKey(true)
    val id: Int = 0,
    val query: String,
    val timestamp: Long = System.currentTimeMillis()
)