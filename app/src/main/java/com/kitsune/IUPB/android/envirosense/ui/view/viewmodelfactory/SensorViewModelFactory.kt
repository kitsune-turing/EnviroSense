package com.kitsune.IUPB.android.envirosense.ui.view.viewmodelfactory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kitsune.IUPB.android.envirosense.data.repository.SensorRepository
import com.kitsune.IUPB.android.envirosense.ui.view.viewmodel.SensorViewModel


class SensorViewModelFactory(private val repository: SensorRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SensorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SensorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}