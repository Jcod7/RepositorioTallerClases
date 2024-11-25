package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import android.widget.CheckBox
import android.widget.TextView

class FormularioActivity : AppCompatActivity() {

    private lateinit var etNombres: TextInputEditText
    private lateinit var etApellidos: TextInputEditText
    private lateinit var etCorreo: TextInputEditText
    private lateinit var etTelefono1: TextInputEditText
    private lateinit var etTelefono2: TextInputEditText

    private lateinit var cbHombre: CheckBox
    private lateinit var cbMujer: CheckBox
    private lateinit var btnEnviar: MaterialButton
    private lateinit var tvDatos: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

        // Inicializar vistas
        initViews()

        // Configurar listeners
        setupListeners()
    }

    private fun initViews() {
        etNombres = findViewById(R.id.etNombres)
        etApellidos = findViewById(R.id.etApellidos)
        etCorreo = findViewById(R.id.etCorreo)
        etTelefono1 = findViewById(R.id.etTelefono1)
        etTelefono2 = findViewById(R.id.etTelefono2)
        cbHombre = findViewById(R.id.cbHombre)
        cbMujer = findViewById(R.id.cbMujer)
        btnEnviar = findViewById(R.id.btnEnviar)
        tvDatos = findViewById(R.id.textView2)
    }

    private fun setupListeners() {
        cbHombre.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) cbMujer.isChecked = false
        }

        cbMujer.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) cbHombre.isChecked = false
        }

        btnEnviar.setOnClickListener {
            if (validateFields()) {
                displayData()
                Toast.makeText(this, "Datos enviados correctamente.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateFields(): Boolean {
        if (etNombres.text.isNullOrEmpty()) {
            etNombres.error = "Este campo es requerido"
            return false
        }

        if (etApellidos.text.isNullOrEmpty()) {
            etApellidos.error = "Este campo es requerido"
            return false
        }

        if (etCorreo.text.isNullOrEmpty()) {
            etCorreo.error = "Este campo es requerido"
            return false
        }

        if (etTelefono1.text.isNullOrEmpty()) {
            etTelefono1.error = "Este campo es requerido"
            return false
        }

        if (etTelefono2.text.isNullOrEmpty()) {
            etTelefono2.error = "Este campo es requerido"
            return false
        }

        if (!cbHombre.isChecked && !cbMujer.isChecked) {
            Toast.makeText(this, "Por favor seleccione un género", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun displayData() {
        val nombres = etNombres.text.toString()
        val apellidos = etApellidos.text.toString()
        val correo = etCorreo.text.toString()
        val telefono1 = etTelefono1.text.toString()
        val telefono2 = etTelefono2.text.toString()
        val genero = if (cbHombre.isChecked) "Hombre" else "Mujer"

        val datos = """
            Nombres: $nombres
            Apellidos: $apellidos
            Correo: $correo
            Teléfono 1: $telefono1
            Teléfono 2: $telefono2
            Género: $genero
        """.trimIndent()

        tvDatos.text = datos
    }
}
