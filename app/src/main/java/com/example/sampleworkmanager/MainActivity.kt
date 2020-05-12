package com.example.sampleworkmanager

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val workManager = WorkManager.getInstance(this)
        val constraints = Constraints.Builder().setRequiredNetworkType(
            NetworkType.CONNECTED
        ).build()
        val workRequest = OneTimeWorkRequest.Builder(SyncWorker::class.java).setConstraints(
            constraints
        ).build()

        startSyncing.setOnClickListener {
            workManager.enqueueUniqueWork(SyncWorker.workTag, ExistingWorkPolicy.KEEP, workRequest)
        }
        workManager.getWorkInfoByIdLiveData(workRequest.id).observe(this, Observer { workInfo ->
            val hasProgress = workInfo.progress.keyValueMap.isNotEmpty()
            var message = ""
            message = if (hasProgress) {
                val dataSent = workInfo.progress.getString(SyncWorker.progressKey)
                "Data obtained:${dataSent}"
            } else {
                "No progress data from worker!"
            }
            if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                message = "Sync Ended!!"
            }
            statusTextView.text = message
        })
    }
}
