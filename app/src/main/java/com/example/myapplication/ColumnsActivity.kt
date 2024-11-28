package com.example.myapplication

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ColumnsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_columns)

        // Referencias a los elementos de la UI
        val profileImage = findViewById<ImageView>(R.id.profileImage)
        val inputField = findViewById<EditText>(R.id.inputField)
        val submitButton = findViewById<Button>(R.id.submitButton)
        val backButton = findViewById<Button>(R.id.backButton)  // Botón de regresar

        // Acción al presionar la imagen
        profileImage.setOnClickListener {
            Toast.makeText(this, "¡Imagen de perfil seleccionada!", Toast.LENGTH_SHORT).show()
        }

        // Acción al presionar el botón de "Registrar"
        submitButton.setOnClickListener {
            val input = inputField.text.toString()
            if (input.isNotEmpty()) {
                Toast.makeText(this, "Bienvenido, $input!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Por favor, ingrese su nombre.", Toast.LENGTH_SHORT).show()
            }
        }

        // Acción al presionar el botón de "Regresar"
        backButton.setOnClickListener {
            finish() // Esto cierra la actividad actual y regresa a la anterior
        }
    }
}
