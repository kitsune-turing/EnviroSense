package com.kitsune.IUPB.android.envirosense.data.service


import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.DocumentSnapshot
import com.kitsune.IUPB.android.envirosense.data.repository.FirestoreRepository
import com.kitsune.IUPB.android.envirosense.model.SensorData


class SensorService(appContext: Context, workerParams: WorkerParameters) :
        Worker(appContext, workerParams) {

    private val firestoreRepository = FirestoreRepository()
    private var lastVisibleSO2: DocumentSnapshot? = null
    private var lastVisibleCO2: DocumentSnapshot? = null
    private var lastVisibleNO2: DocumentSnapshot? = null


    override fun doWork(): Result {
        // Obtener datos para SO2, CO2 y NO2
        fetchData("SO2", lastVisibleSO2)
        fetchData("CO2", lastVisibleCO2)
        fetchData("NO2", lastVisibleNO2)
        return Result.success()
    }

    private fun fetchData(sensorType: String, lastVisible: DocumentSnapshot?) {
        firestoreRepository.getSensorData(sensorType, lastVisible, { data, lastVisibleDoc ->
            saveToCache(sensorType, data)
            updateLastVisible(sensorType, lastVisibleDoc)
        }, {
        })
    }

    private fun saveToCache(sensorType: String, data: List<SensorData>) {
        // Guardar en caché (SharedPreferences, Room, etc.)
        // Aquí puede implementar la lógica de caché, como usar Room para guardar los datos localmente.
    }

    private fun updateLastVisible(sensorType: String, lastVisibleDoc: DocumentSnapshot?) {
        when (sensorType) {
            "SO2" -> lastVisibleSO2 = lastVisibleDoc
            "CO2" -> lastVisibleCO2 = lastVisibleDoc
            "NO2" -> lastVisibleNO2 = lastVisibleDoc
        }
    }
}