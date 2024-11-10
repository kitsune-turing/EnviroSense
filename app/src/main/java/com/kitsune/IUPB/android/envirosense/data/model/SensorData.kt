package com.kitsune.IUPB.android.envirosense.model


data class SensorData(
    val sensorType: String = "",
    val value: Double = 0.0,
    val timestamp: Long = 0L,
    val municipality: String = ""
)