package com.kitsune.IUPB.android.envirosense.utils

import android.graphics.Color

object ChartUtil {
    fun getCustomColors(): List<Int> {
        return listOf(
            Color.parseColor("#f5c8bf"),
            Color.parseColor("#e0d2c5"),
            Color.parseColor("#cad9ca"),
            Color.parseColor("#a7e3c1"),
            Color.parseColor("#495453"),
            Color.parseColor("#d3c6cc"),
            Color.parseColor("#e2c3c6"),
            Color.parseColor("#eecfc4"),
            Color.parseColor("#f8e6c6"),
            Color.parseColor("#ffffcc")
        )
    }
}