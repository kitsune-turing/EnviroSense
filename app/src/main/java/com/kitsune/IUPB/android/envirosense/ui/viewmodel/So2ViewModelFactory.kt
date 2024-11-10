package com.kitsune.IUPB.android.envirosense.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kitsune.IUPB.android.envirosense.data.repository.SensorRepository


class So2ViewModelFactory(
    private val sensorRepository: SensorRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(So2ViewModel::class.java)) {
            return So2ViewModel(sensorRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}