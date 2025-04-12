package com.jadesoft.javhub.data.di

import android.content.Context
import androidx.room.Room
import com.jadesoft.javhub.data.db.JavHubDatabase
import com.jadesoft.javhub.data.db.dao.JavHubDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): JavHubDatabase {
        return Room.databaseBuilder(
            context,
            JavHubDatabase::class.java,
            "javhub_database.db"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    fun provideDao(
        database: JavHubDatabase
    ): JavHubDao = database.dao

}