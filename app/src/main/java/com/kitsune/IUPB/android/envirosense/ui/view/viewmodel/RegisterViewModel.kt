package com.kitsune.IUPB.android.envirosense.ui.viewmodel


import androidx.lifecycle.ViewModel
import com.kitsune.IUPB.android.envirosense.data.repository.UserRepository
import com.kitsune.IUPB.android.envirosense.data.model.User


class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun registerUser(name: String, email: String, password: String, onResult: (Boolean, User?) -> Unit) {
        userRepository.registerUser(email, password) { isSuccess, user ->
            if (isSuccess && user != null) {
                val userData = User(uid = user.uid, email = user.email, displayName = name)
                userRepository.saveUserData(userData)
                onResult(true, userData)
            } else {
                onResult(false, null)
            }
        }
    }
}