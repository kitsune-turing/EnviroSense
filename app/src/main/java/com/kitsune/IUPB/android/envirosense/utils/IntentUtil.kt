package com.kitsune.IUPB.android.envirosense.utils


import android.content.Context
import android.content.Intent
import android.util.Log


object IntentUtil {

    // Método único para navegar a cualquier actividad
    fun navigateToActivity(context: Context, activityClass: Class<*>) {
        val intent = Intent(context, activityClass)
        Log.d("IntentUtil", "Open new activity")
        context.startActivity(intent)
    }
}