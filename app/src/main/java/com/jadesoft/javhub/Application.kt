package com.jadesoft.javhub

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.jadesoft.javhub.data.db.JavHubDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {

    override fun onCreate() {

        super.onCreate()
        Log.d("JavApplication", "Application is initialized")
    }

}