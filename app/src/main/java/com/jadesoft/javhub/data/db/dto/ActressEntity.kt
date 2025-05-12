package com.jadesoft.javhub.data.db.dto

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["code"], unique = true)]
)
data class ActressEntity(
    @PrimaryKey(true)
    val id: Int,
    val code: String,
    val name: String,
    val avatar: String,
    val censored: Boolean,
    val createDate: String
)