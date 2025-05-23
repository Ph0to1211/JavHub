package com.jadesoft.javhub.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jadesoft.javhub.data.db.dao.JavHubDao
import com.jadesoft.javhub.data.db.dto.ActressEntity
import com.jadesoft.javhub.data.db.dto.HistoryEntity
import com.jadesoft.javhub.data.db.dto.MovieEntity
import com.jadesoft.javhub.data.db.dto.SearchHistoryEntity

@Database(
    entities = [
        MovieEntity::class,
        ActressEntity::class,
        HistoryEntity::class,
        SearchHistoryEntity::class
    ],
    version = 3,
    exportSchema = false
)

abstract class JavHubDatabase: RoomDatabase() {
    abstract val dao: JavHubDao
}