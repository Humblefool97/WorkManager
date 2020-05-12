package com.example.sampleworkmanager

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import javax.inject.Inject

class CustomApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var customWorkerFactory: CustomWorkerFactory

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
        appComponent.inject(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(customWorkerFactory)
            .build()
    }
}