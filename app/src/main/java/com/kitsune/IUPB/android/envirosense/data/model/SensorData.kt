package com.kitsune.IUPB.android.envirosense.data.model

/**
 * Sensor data model for access to information with model sensor
 */
data class SensorData(
    val date: String = "",
    val value: Float = 0f,
    val municipality: String = ""
)