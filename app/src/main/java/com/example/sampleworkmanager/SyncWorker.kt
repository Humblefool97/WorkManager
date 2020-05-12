package com.example.sampleworkmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.util.*
import java.util.concurrent.LinkedBlockingDeque

class SyncWorker(
    val context: Context,
    val params: WorkerParameters,
    val dummyDependency: DummyDependency
) : Worker(context, params) {
    private val jobQueue: Queue<Int> = LinkedBlockingDeque<Int>()

    init {
        jobQueue.add(1)
        jobQueue.add(2)
        jobQueue.add(3)
        jobQueue.add(4)
    }

    override fun doWork(): Result {
        while (jobQueue.isNotEmpty()) {
            //Network call
            Thread.sleep(600)
            handleProgress(jobQueue.peek())
        }
        return Result.success()
    }

    private fun handleProgress(value: Int?) {
        val workData = workDataOf(progressKey to "JobId:${value}")
        setProgressAsync(workData)
        jobQueue.poll()
    }

    companion object {
        const val progressKey = "progressKey"
        const val workTag = "sync"
    }
}