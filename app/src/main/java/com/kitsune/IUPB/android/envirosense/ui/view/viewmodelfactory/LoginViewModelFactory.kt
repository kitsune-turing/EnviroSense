package com.kitsune.IUPB.android.envirosense.ui.view.viewmodelfactory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kitsune.IUPB.android.envirosense.data.repository.UserRepository
import com.kitsune.IUPB.android.envirosense.ui.view.viewmodel.LoginViewModel


class LoginViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {

    override  fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}