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
    private var initialSteps: Int = 0 // Almacena el valor inicial del sensor
    private var currentSteps: Int = 0 // Pasos desde el inicio del conteo
    private var countingSteps = false
    private lateinit var tv_steps: TextView
    private lateinit var btn_count_steps: Button
    private lateinit var tv_header: TextView // Campo de encabezado

    @SuppressLint("StringFormatMatches")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_podometro)

        // Inicialización del SensorManager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        // Referencias a los elementos de la interfaz
        tv_header = findViewById(R.id.textViewHeader) // Encabezado
        tv_steps = findViewById(R.id.textViewSteps)
        btn_count_steps = findViewById(R.id.buttonCountSteps)

        // Verificar si el dispositivo tiene un sensor de pasos
        if (stepSensor == null) {
            tv_steps.text = getString(R.string.no_se_detect_sensor_de_pasos)
        }

        // Configuración del botón para iniciar o detener el conteo de pasos
        btn_count_steps.setOnClickListener {
            if (!countingSteps) {
                countingSteps = true
                initialSteps = 0 // Reiniciar pasos iniciales
                currentSteps = 0
                sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
                tv_steps.text = getString(R.string.contando_pass)
                btn_count_steps.text = getString(R.string.detener_conte)
            } else {
                countingSteps = false
                sensorManager.unregisterListener(this)
                tv_steps.text = getString(R.string.pass, currentSteps)
                btn_count_steps.text = getString(R.string.iniciar_con)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Si estamos contando pasos, registramos el listener al retomar la actividad
        if (countingSteps) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        // Si estamos contando pasos, desregistramos el listener al pausar la actividad
        if (countingSteps) {
            sensorManager.unregisterListener(this)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            // La primera vez que se recibe un evento, se guarda el valor inicial
            if (initialSteps == 0) {
                initialSteps = event.values[0].toInt()
            }
            // Calcular los pasos actuales como la diferencia entre el valor inicial y el actual
            currentSteps = event.values[0].toInt() - initialSteps
            // Mostrar los pasos actuales
            tv_steps.text = "Pasos: $currentSteps"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
