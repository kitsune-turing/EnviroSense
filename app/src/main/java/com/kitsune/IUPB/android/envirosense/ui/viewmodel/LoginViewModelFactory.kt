package com.kitsune.IUPB.android.envirosense.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kitsune.IUPB.android.envirosense.data.repository.UserRepository


class LoginViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override  fun <T : ViewModel> create(modelClass : Class<T>) : T {

        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Uknown view model class")
    }
}