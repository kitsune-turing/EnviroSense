package com.kitsune.IUPB.android.envirosense.data.repository


import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.kitsune.IUPB.android.envirosense.data.model.SensorData
import kotlinx.coroutines.tasks.await


class SensorRepository(private val firestore: FirebaseFirestore) {
    suspend fun fetchSensorData(sensorType: String): List<SensorData> {
        val dataList = mutableListOf<SensorData>()
        try {
            val querySnapshot = firestore
                .collection("sensors")
                .document(sensorType)
                .collection("data")
                .get()
                .await()

            Log.d(sensorType, "Documents size: ${querySnapshot.size()}")

            if (querySnapshot.isEmpty) {
                Log.d(sensorType, "No data found.")
            }

            for (document in querySnapshot.documents) {
                val date = document.getString("date") ?: ""
                val municipality = document.getString("municipality") ?: ""
                val value = document.getDouble("value")?.toFloat() ?: 0f
                dataList.add(SensorData(date = date, municipality = municipality, value = value))
                Log.d(sensorType, "Document: $municipality, $date, $value")
            }
        } catch (e: Exception) {
            Log.e(sensorType, "Error fetching data", e)
        }
        return dataList
    }
}