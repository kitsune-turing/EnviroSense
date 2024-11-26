package com.kitsune.IUPB.android.envirosense.data.repository


import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.AuthCredential
import com.kitsune.IUPB.android.envirosense.data.model.User

/**
 * Class information for user repository data
 */
class UserRepository(private val firebaseAuth: FirebaseAuth) {
    /**
     * Get auth user
     */
    fun authUser(email: String, password: String, onResult: (Boolean, FirebaseUser?) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, firebaseAuth.currentUser)
                } else {
                    onResult(false, null)
                }
            }
    }

    /**
     * Login with google
     */
    fun loginWithGoogle(idToken: String, onResult: (Boolean, FirebaseUser?) -> Unit) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, firebaseAuth.currentUser)
                } else {
                    onResult(false, null)
                }
            }
    }

    /**
     * Register user
     */
    fun registerUser(email: String, password: String, onResult: (Boolean, FirebaseUser?) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, firebaseAuth.currentUser)
                } else {
                    onResult(false, null)
                }
            }
    }

    /**
     * Create user into collection
     */
    fun saveUserData(user: User) {
        val db = FirebaseFirestore.getInstance()
        val userMap = hashMapOf(
            "uid" to user.uid,
            "email" to user.email,
            "displayName" to user.displayName
        )
        db.collection("users").document(user.uid).set(userMap)
            .addOnSuccessListener { Log.d("UserRepository", "User data saved") }
            .addOnFailureListener { e -> Log.w("UserRepository", "Error saving user data", e) }
    }
}