package com.kitsune.IUPB.android.envirosense.data.session


import android.content.Context
import android.content.SharedPreferences


class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    private val KEY_USER_ID = "user_id"
    private val KEY_EMAIL = "email"
    private val KEY_LOGGED_IN = "logged_in"


    /***
     * Save user state and uuid into shared preferences
     */
    fun saveUserSession(userId: String, email: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USER_ID, userId)
        editor.putString(KEY_EMAIL, email)
        editor.putBoolean(KEY_LOGGED_IN, true)
        editor.apply()
    }


    /***
     * Get user shared preferences
     */
    fun getUserSession(): Pair<String, String>? {
        val userId = sharedPreferences.getString(KEY_USER_ID, null)
        val email = sharedPreferences.getString(KEY_EMAIL, null)
        return if (userId != null && email != null) {
            Pair(userId, email)
        } else {
            null
        }
    }


    /***
     * User login in plataform
     */
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_LOGGED_IN, false)
    }


    /**
     * Delete user into shared preferences
     */
    fun clearSession() {
        val editor = sharedPreferences.edit()
        editor.remove(KEY_USER_ID)
        editor.remove(KEY_EMAIL)
        editor.putBoolean(KEY_LOGGED_IN, false)
        editor.apply()
    }
}