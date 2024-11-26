package com.kitsune.IUPB.android.envirosense.ui.view


import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.kitsune.IUPB.android.envirosense.R
import com.kitsune.IUPB.android.envirosense.utils.IntentUtil


class SelectorActivity : AppCompatActivity() {
    private lateinit var btnTemperature: ImageButton
    private lateinit var btnSo2: ImageButton
    private lateinit var btnHumedaty: ImageButton
    private lateinit var btnCo: ImageButton
    private lateinit var btnNo2: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selector)

        // Referencia a los botones
        btnTemperature = findViewById(R.id.btn_temperature)
        btnSo2 = findViewById(R.id.btn_so2)
        btnHumedaty = findViewById(R.id.btn_humedaty)
        btnCo = findViewById(R.id.btn_co)
        btnNo2 = findViewById(R.id.btn_no2)


        // Listener para el botón de temperatura
        btnTemperature.setOnClickListener {
            IntentUtil.navigateToActivity(this, TemperatureActivity::class.java)
        }

        // Listener para el botón de SO2
        btnSo2.setOnClickListener {
            IntentUtil.navigateToActivity(this, So2Activity::class.java)
        }

        // Listener para el botón de humedad
        btnHumedaty.setOnClickListener {
            IntentUtil.navigateToActivity(this, HumedatyActivity::class.java)
        }

        // Listener para el botón de CO
        btnCo.setOnClickListener {
            IntentUtil.navigateToActivity(this, CoActivity::class.java)
        }

        // Listener para el botón de NO2
        btnNo2.setOnClickListener {
            IntentUtil.navigateToActivity(this, No2Activity::class.java)
        }

    }
}