package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.OutputStream

@Suppress("DEPRECATION")
class CamaraActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private var capturedBitmap: Bitmap? = null  // Variable para almacenar la imagen capturada

    // Registrador de actividad para la cámara
    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                // Se obtiene la imagen capturada en forma de Bitmap
                capturedBitmap = result.data?.extras?.get("data") as Bitmap
                // Establecer la imagen en el ImageView
                imageView.setImageBitmap(capturedBitmap)
            }
        }

    // Método que se ejecuta cuando se crea la actividad
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camara)

        // Inicializar el ImageView
        imageView = findViewById(R.id.imageView)

        val btnTakePicture = findViewById<Button>(R.id.buttonTakePicture)
        val btnSavePicture = findViewById<Button>(R.id.buttonSavePicture)
        val btnRegresar = findViewById<Button>(R.id.btnRegresar) // Referencia al botón regresar

        // Listener para tomar la foto
        btnTakePicture.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePictureLauncher.launch(cameraIntent)
        }

        // Listener para guardar la foto
        btnSavePicture.setOnClickListener {
            capturedBitmap?.let { bitmap ->
                saveImageToGallery(bitmap)
            } ?: run {
                Toast.makeText(this, "No hay imagen para guardar", Toast.LENGTH_SHORT).show()
            }
        }

        // Listener para el botón regresar
        btnRegresar.setOnClickListener {
            finish() // Cierra la actividad actual y regresa a la anterior
        }
    }

    // Método para guardar la imagen en la galería
    private fun saveImageToGallery(bitmap: Bitmap) {
        try {
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "captura_${System.currentTimeMillis()}.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyAppImages")
            }

            val contentResolver = contentResolver
            val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            uri?.let {
                val outputStream: OutputStream? = contentResolver.openOutputStream(it)
                if (outputStream != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }
                outputStream?.close()

                Toast.makeText(this, "Imagen guardada en la galería", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error al guardar la imagen", Toast.LENGTH_SHORT).show()
        }
    }
}
