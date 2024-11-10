package com.kitsune.IUPB.android.envirosense.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kitsune.IUPB.android.envirosense.model.SensorData

class SensorViewModel : ViewModel() {
    private val _so2Data = MutableLiveData<List<SensorData>>()
    val so2Data: LiveData<List<SensorData>> get() = _so2Data

    private val _co2Data = MutableLiveData<List<SensorData>>()
    val co2Data: LiveData<List<SensorData>> get() = _co2Data

    private val _no2Data = MutableLiveData<List<SensorData>>()
    val no2Data: LiveData<List<SensorData>> get() = _no2Data



    // Actualizar los datos
    fun setSO2Data(data: List<SensorData>) {
        _so2Data.value = data
    }

    fun setCO2Data(data: List<SensorData>) {
        _co2Data.value = data
    }

    fun setNO2Data(data: List<SensorData>) {
        _no2Data.value = data
    }
}