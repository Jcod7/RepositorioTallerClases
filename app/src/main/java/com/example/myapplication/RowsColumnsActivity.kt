package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class RowsColumnsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rows_columns) // Asignación del XML principal

        // Obtener el botón desde el layout
        val buttonBack = findViewById<Button>(R.id.buttonBack)

        // Configurar el listener del botón
        buttonBack.setOnClickListener {
            onBackPressed() // Regresar a la actividad anterior
        }
    }
}
