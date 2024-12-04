package com.example.myapplication
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*

@Suppress("DEPRECATION")
class GpsActivity : AppCompatActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val locationPermissionRequestCode = 101

    @SuppressLint("MissingInflatedId", "StringFormatMatches")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps)

        // Inicializar FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val btnGpsCalcular = findViewById<Button>(R.id.button1)
        val tvMensaje = findViewById<TextView>(R.id.textView)

        // Verificar permisos al inicio
        if (!isLocationPermissionGranted()) {
            requestLocationPermission()
        }

        // Acción del botón para obtener la ubicación
        btnGpsCalcular.setOnClickListener {
            tvMensaje.text = getString(R.string.obteniendo_ubicacion)
            findLocation(tvMensaje)
        }

        // Acción del botón de regresar
        setupRegresarButton()
    }

    // Función para configurar la acción de regresar
    private fun setupRegresarButton() {
        val buttonRegresar = findViewById<Button>(R.id.buttonRegresar)
        buttonRegresar.setOnClickListener {
            finish()  // Cierra la actividad actual
        }
    }

    // Verificar si los permisos de ubicación están concedidos
    private fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    // Solicitar permisos de ubicación si no están concedidos
    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            locationPermissionRequestCode
        )
    }

    // Manejar la respuesta de la solicitud de permisos
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == locationPermissionRequestCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                findLocation(findViewById(R.id.textView))
            } else {
                Toast.makeText(this, R.string.permission_denied_message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("MissingPermission", "StringFormatInvalid")
    private fun findLocation(tvMensaje: TextView) {
        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                Log.d("GPS", "Ubicación obtenida: Latitud ${location.latitude}, Longitud ${location.longitude}")
                if (location.latitude != 0.0 && location.longitude != 0.0) {
                    val address = getAddress(location.latitude, location.longitude)
                    val msg = "Latitud: ${location.latitude}, Longitud: ${location.longitude}\nDirección: $address"
                    tvMensaje.text = msg
                    Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
                } else {
                    tvMensaje.text = getString(R.string.ubicaci_n_no_v_lida)
                }
            } else {
                tvMensaje.text = getString(R.string.no_se_pudo_obtener_la_ubicaci_n)
            }
        }
    }

    private fun getAddress(lat: Double, lng: Double): String {
        val geocoder = Geocoder(this)
        return try {
            val list = geocoder.getFromLocation(lat, lng, 1)
            list?.firstOrNull()?.getAddressLine(0) ?: getString(R.string.direccion_desconocida)
        } catch (e: Exception) {
            Log.e("GPS", "Error al obtener dirección", e)
            getString(R.string.error_obteniendo_direccion)
        }
    }
}
