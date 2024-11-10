package com.kitsune.IUPB.android.envirosense.data.repository


import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.kitsune.IUPB.android.envirosense.model.SensorData


class FirestoreRepository : SensorRepository {
    private val db = FirebaseFirestore.getInstance()

    override fun getSensorData(
        sensorType: String,
        lastVisible: DocumentSnapshot?,
        onSuccess: (List<SensorData>, DocumentSnapshot?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val query = db.collection("sensors")
            .document(sensorType)
            .collection("data")
            .orderBy("date", Query.Direction.ASCENDING)
            .limit(20)

        lastVisible?.let {
            query.startAfter(it)
        }

        query.get()
            .addOnSuccessListener { snapshot ->
                if (!snapshot.isEmpty) {
                    val data = snapshot.documents.mapNotNull { it.toObject(SensorData::class.java) }
                    val lastVisibleDoc = snapshot.documents.lastOrNull()
                    onSuccess(data, lastVisibleDoc)
                }
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }
}