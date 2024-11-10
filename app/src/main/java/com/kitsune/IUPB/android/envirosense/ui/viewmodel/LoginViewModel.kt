package com.kitsune.IUPB.android.envirosense.ui.viewmodel


import androidx.lifecycle.ViewModel
import com.kitsune.IUPB.android.envirosense.data.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import com.kitsune.IUPB.android.envirosense.data.model.User


class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun loginUser(email: String, password: String, onResult: (Boolean, FirebaseUser?) -> Unit) {
        userRepository.authUser(email, password) { isSuccess, user ->
            onResult(isSuccess, user)
        }
    }

    fun loginWithGoogle(idToken: String, onResult: (Boolean, FirebaseUser?) -> Unit) {
        userRepository.loginWithGoogle(idToken) { isSuccess, user ->
            onResult(isSuccess, user)
        }
    }

    fun registerUser(email: String, password: String, onResult: (Boolean, FirebaseUser?) -> Unit) {
        userRepository.registerUser(email, password) { isSuccess, user ->
            onResult(isSuccess, user)
        }
    }

    fun saveUser(user: FirebaseUser) {
        val userData = convertFirebaseUserToUser(user)
        userRepository.saveUserData(userData)
    }


    private fun convertFirebaseUserToUser(firebaseUser: FirebaseUser): User {
        return User(
            uid = firebaseUser.uid,
            email = firebaseUser.email,
            displayName = firebaseUser.displayName
        )
    }
}