package com.jadesoft.javhub.data.db.dto

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["code"], unique = true)]
)
data class HistoryEntity(
    @PrimaryKey(true)
    val id: Int,
    val code: String,
    val title: String,
    val cover: String,
    val createDate: String
)