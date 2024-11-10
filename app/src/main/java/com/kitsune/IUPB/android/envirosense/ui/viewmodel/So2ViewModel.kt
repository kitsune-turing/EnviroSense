package com.kitsune.IUPB.android.envirosense.ui.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kitsune.IUPB.android.envirosense.data.repository.SensorRepository
import com.kitsune.IUPB.android.envirosense.model.SensorData


class So2ViewModel(private val sensorRepository: SensorRepository) : ViewModel() {
    private val _so2Data = MutableLiveData<List<SensorData>>()
    val so2Data: LiveData<List<SensorData>> get() = _so2Data

    fun fetchSo2Data() {
        sensorRepository.getSensorData(
            sensorType = "SO2",
            lastVisible = null,
            onSuccess = { data, _ ->
                Log.d("So2ViewModel", "Data fetched successfully: $data")
                _so2Data.value = data
            },
            onFailure = { exception ->
                Log.e("So2ViewModel", "Error fetching data: ${exception.message}")
            }
        )
    }
}