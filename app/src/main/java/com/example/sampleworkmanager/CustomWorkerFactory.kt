package com.example.sampleworkmanager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomWorkerFactory @Inject constructor(private val dummyDependency: DummyDependency) :
    WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return SyncWorker(appContext, workerParameters, dummyDependency)
    }

}