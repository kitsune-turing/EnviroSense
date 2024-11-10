package com.kitsune.IUPB.android.envirosense.data.repository


import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.DocumentSnapshot
import com.kitsune.IUPB.android.envirosense.model.SensorData


class SensorRepositoryImpl : SensorRepository {
    private val firestore = FirebaseFirestore.getInstance()


    override fun getSensorData(
        sensorType: String,
        lastVisible: DocumentSnapshot?,
        onSuccess: (List<SensorData>, DocumentSnapshot?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val query = firestore.collection("sensorData")
            .whereEqualTo("sensorType", sensorType)
            .limit(20)

        lastVisible?.let { query.startAfter(it) }

        query.get()
            .addOnSuccessListener { querySnapshot ->
                val sensorDataList = querySnapshot.mapNotNull { document ->
                    document.toObject(SensorData::class.java)
                }
                onSuccess(sensorDataList, querySnapshot.documents.lastOrNull())
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}