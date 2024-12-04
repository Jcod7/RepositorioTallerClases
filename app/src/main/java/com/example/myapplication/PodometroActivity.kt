package com.example.myapplication

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PodometroActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var stepSensor: Sensor? = null
    private var initialSteps: Int = 0
    private var currentSteps: Int = 0
    private var countingSteps = false
    private lateinit var tvSteps: TextView
    private lateinit var btnCountSteps: Button
    private lateinit var tvHeader: TextView
    private lateinit var btnBack: Button // Botón de regreso

    @SuppressLint("StringFormatMatches", "StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_podometro)

        // Inicialización del SensorManager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        // Referencias a los elementos de la interfaz
        tvHeader = findViewById(R.id.textViewHeader) // Encabezado
        tvSteps = findViewById(R.id.textViewSteps)
        btnCountSteps = findViewById(R.id.buttonCountSteps)
        btnBack = findViewById(R.id.buttonBack) // Botón Regresar

        // Verificar si el dispositivo tiene un sensor de pasos
        if (stepSensor == null) {
            tvSteps.text = getString(R.string.no_se_detect_sensor_de_pasos)
        }

        // Configuración del botón para iniciar o detener el conteo de pasos
        btnCountSteps.setOnClickListener {
            if (!countingSteps) {
                countingSteps = true
                initialSteps = 0
                currentSteps = 0
                sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
                tvSteps.text = getString(R.string.contando_pass)
                btnCountSteps.text = getString(R.string.detener_conte)
            } else {
                countingSteps = false
                sensorManager.unregisterListener(this)
                tvSteps.text = getString(R.string.pass, currentSteps)
                btnCountSteps.text = getString(R.string.iniciar_con)
            }
        }

        // Configuración del botón "Regresar"
        btnBack.setOnClickListener {
            finish() // Esto termina la actividad y regresa a la anterior
        }
    }

    override fun onResume() {
        super.onResume()
        if (countingSteps) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        if (countingSteps) {
            sensorManager.unregisterListener(this)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            if (initialSteps == 0) {
                initialSteps = event.values[0].toInt()
            }
            currentSteps = event.values[0].toInt() - initialSteps
            tvSteps.text = "Pasos: $currentSteps"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
