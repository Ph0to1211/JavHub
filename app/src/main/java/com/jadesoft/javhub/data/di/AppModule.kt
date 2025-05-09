package com.jadesoft.javhub.data.di

import android.content.Context
import com.jadesoft.javhub.data.api.ApiService
import com.jadesoft.javhub.data.preferences.PreferencesManager
import com.jadesoft.javhub.data.repository.ExploreRepository
import com.jadesoft.javhub.data.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharePreferencesManager(@ApplicationContext context: Context): PreferencesManager {
        return PreferencesManager(context)
    }

    @Provides
    @Singleton
    fun provideFrontRepository(service: ApiService): ExploreRepository {
        return ExploreRepository(service)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(): HomeRepository {
        return HomeRepository()
    }

//    @Provides
//    @Singleton
//    fun provideDetailRepository(apiService: ApiService): DetailRepository {
//        return DetailRepository(apiService)
//    }

}