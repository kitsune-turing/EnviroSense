package com.kitsune.IUPB.android.envirosense.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.kitsune.IUPB.android.envirosense.R

class NavBarActivity : AppCompatActivity() {

    private lateinit var btnReturn: ImageView
    private lateinit var btnExit: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navegation_bar)

        // boton para regresar a la pantalla principal
        btnReturn.setOnClickListener{
            val intent = Intent(this, SelectorActivity::class.java)
            startActivity(intent)
        }

        btnExit.setOnClickListener {
            cerrarApp() // Llama a la función para cerrar la app
        }
    }

    // función para cerrar la app
    private fun cerrarApp() {
        finishAffinity() // Finaliza todas las actividades y cierra la aplicación
    }
}