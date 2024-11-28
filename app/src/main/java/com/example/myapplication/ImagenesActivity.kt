package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ImagenesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imagenes)

        // Referencias de los elementos de la UI
        val btnMostrarTexto: Button = findViewById(R.id.btnMostrarTexto)
        val textoAparecer: TextView = findViewById(R.id.textoAparecer)

        // Descripción de la imagen
        val descripcionImagen = "Un encantador bulldog francés con expresión dulce y curiosa. Su rostro arrugado y orejas puntiagudas resaltan sobre un fondo neutro, transmitiendo ternura y carácter en perfecta armonía."

        // Configurando el botón para que muestre la descripción en el TextView
        btnMostrarTexto.setOnClickListener {
            if (textoAparecer.visibility == View.VISIBLE) {
                textoAparecer.visibility = View.GONE // Ocultar el texto
            } else {
                textoAparecer.text = descripcionImagen // Asignar descripción al TextView
                textoAparecer.visibility = View.VISIBLE // Mostrar el texto
            }
        }
        val backButton = findViewById<Button>(R.id.backButton)  // Botón de regresar
        // Acción al presionar el botón de "Regresar"
        backButton.setOnClickListener {
            finish() // Esto cierra la actividad actual y regresa a la anterior
        }

    }
}

