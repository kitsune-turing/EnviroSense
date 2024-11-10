package com.kitsune.IUPB.android.envirosense.utils


import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.kitsune.IUPB.android.envirosense.data.service.SensorService
import java.util.concurrent.TimeUnit


object WorkManagerUtil {

    // Función para programar el trabajo periódico cada 5 minutos
    fun schedulePeriodicDataFetch() {
        val workRequest = OneTimeWorkRequest.Builder(SensorService::class.java)
            .setInitialDelay(5, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance().enqueue(workRequest)


        // Observamos el estado del trabajo
        WorkManager.getInstance().getWorkInfoByIdLiveData(workRequest.id).observeForever { workInfo ->
            if (workInfo != null && workInfo.state.isFinished) {
                schedulePeriodicDataFetch()
            }
        }
    }
}