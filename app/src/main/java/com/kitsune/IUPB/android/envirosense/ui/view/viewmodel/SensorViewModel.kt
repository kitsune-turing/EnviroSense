package com.kitsune.IUPB.android.envirosense.ui.view.viewmodel


import android.util.Log
import androidx.lifecycle.*
import com.kitsune.IUPB.android.envirosense.data.model.SensorData
import com.kitsune.IUPB.android.envirosense.data.repository.SensorRepository
import kotlinx.coroutines.launch


class SensorViewModel(private val repository: SensorRepository) : ViewModel() {

    private val _sensorDataByMunicipality = MutableLiveData<Map<String, List<SensorData>>>()
    val sensorDataByMunicipality: LiveData<Map<String, List<SensorData>>> get() = _sensorDataByMunicipality

    fun fetchAndGroupData(sensorType: String) {
        viewModelScope.launch {
            val dataList = repository.fetchSensorData(sensorType)
            val groupedData = dataList.groupBy { it.municipality }
            Log.d(sensorType, "Grouped Data: $groupedData")
            _sensorDataByMunicipality.value = groupedData
        }
    }
}