package com.kitsune.IUPB.android.envirosense.data.repository


import com.google.firebase.firestore.DocumentSnapshot
import com.kitsune.IUPB.android.envirosense.model.SensorData


interface SensorRepository {
    fun getSensorData(
        sensorType: String,
        lastVisible: DocumentSnapshot?,
        onSuccess: (List<SensorData>, DocumentSnapshot?) -> Unit,
        onFailure: (Exception) -> Unit
    )
}