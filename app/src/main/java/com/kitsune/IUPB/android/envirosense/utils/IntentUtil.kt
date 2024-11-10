package com.kitsune.IUPB.android.envirosense.utils


import android.content.Context
import android.content.Intent


object IntentUtil {
    // Método único para navegar a cualquier actividad
    fun navigateToActivity(context: Context, activityClass: Class<*>) {
        val intent = Intent(context, activityClass)
        context.startActivity(intent)
    }
}