package com.kitsune.IUPB.android.envirosense.ui.view.viewmodelfactory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kitsune.IUPB.android.envirosense.data.repository.UserRepository
import com.kitsune.IUPB.android.envirosense.ui.view.viewmodel.RegisterViewModel


class RegisterViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}