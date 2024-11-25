package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Botón para acceder a la actividad de formulario
        findViewById<Button>(R.id.btnFormulario).setOnClickListener {
            startActivity(Intent(this, FormularioActivity::class.java))
        }

        // Botón para acceder a la actividad de imágenes
        findViewById<Button>(R.id.btnImagenes).setOnClickListener {
            startActivity(Intent(this, ImagenesActivity::class.java))
        }

        // Botón para acceder a la actividad de filas
        findViewById<Button>(R.id.btnRows).setOnClickListener {
            startActivity(Intent(this, RowsActivity::class.java))
        }

        // Botón para acceder a la actividad de columnas
        findViewById<Button>(R.id.btnColumns).setOnClickListener {
            startActivity(Intent(this, ColumnsActivity::class.java))
        }

        // Botón para acceder a la actividad de filas y columnas
        findViewById<Button>(R.id.btnRowsColumns).setOnClickListener {
            startActivity(Intent(this, RowsColumnsActivity::class.java))
        }

        // Botón para acceder a la actividad de GPS
        findViewById<Button>(R.id.BtnGps).setOnClickListener {
            startActivity(Intent(this, GpsActivity::class.java))
        }

        // Botón para acceder a la actividad de Podómetro
        findViewById<Button>(R.id.BtnPodometro).setOnClickListener {
            startActivity(Intent(this,  PodometroActivity::class.java))
        }

        // Botón para acceder a la actividad de Cámara
        findViewById<Button>(R.id.BtnCamara).setOnClickListener {
            startActivity(Intent(this, CamaraActivity::class.java))
        }
    }
}
