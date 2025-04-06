package com.jadesoft.javhub.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jadesoft.javhub.data.db.dao.JavHubDao
import com.jadesoft.javhub.data.db.dto.HistoryEntity
import com.jadesoft.javhub.data.db.dto.MovieEntity

@Database(
    entities = [
        MovieEntity::class,
        HistoryEntity::class
    ],
    version = 1
)

abstract class JavHubDatabase: RoomDatabase() {
    abstract val dao: JavHubDao
}