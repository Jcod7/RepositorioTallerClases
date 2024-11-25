package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RowsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rows)

        // Elementos de la tarjeta de perfil
        val profileImage = findViewById<ImageView>(R.id.profileImage)
        val profileName = findViewById<TextView>(R.id.profileName)

        // Configuración dinámica de la tarjeta
        profileImage.setImageResource(R.drawable.persona)
        profileName.text = getString(R.string.juan_pez)

        // Botones de acción
        val acceptButton = findViewById<Button>(R.id.acceptButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)
        val infoButton = findViewById<Button>(R.id.infoButton)

        acceptButton.setOnClickListener {
            profileName.text = getString(R.string.aceptado)
        }

        cancelButton.setOnClickListener {
            profileName.text = getString(R.string.cancelado)
        }

        infoButton.setOnClickListener {
            profileName.text = getString(R.string.info_mostrada)
        }
    }
}
