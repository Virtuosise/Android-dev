package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.example.myapplication.R

class gps : AppCompatActivity() {

    private lateinit var fusedClient: FusedLocationProviderClient
    private lateinit var hanup: LocationCallback
    private lateinit var request: LocationRequest
    private lateinit var latText: TextView
    private lateinit var lonText: TextView
    private lateinit var Button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps)

        latText = findViewById(R.id.latt)
        lonText = findViewById(R.id.longt)
        Button = findViewById(R.id.Button)

        fusedClient = LocationServices.getFusedLocationProviderClient(this)

        Button.setOnClickListener { checkPerms() }
        setupReq()
        setupHan()
    }

    private fun setupReq() {
        request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setMinUpdateIntervalMillis(5000)
            .build()
    }

    private fun setupHan() {
        hanup = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.locations.forEach { upUI(it) }
            }
        }
    }

    private fun checkPerms() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), LOC_CODE)
        }
        else {
            start()
        }
    }

    private fun start() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            fusedClient.requestLocationUpdates(request, hanup, Looper.getMainLooper())
        }
    }

    private fun upUI(loc: Location) {
        latText.text = "Широта: ${loc.latitude}"
        lonText.text = "Долгота: ${loc.longitude}"
    }

    override fun onRequestPermissionsResult(reqCode: Int, perms: Array<out String>, results: IntArray) {
        super.onRequestPermissionsResult(reqCode, perms, results)
        if (reqCode == LOC_CODE) {
            if (results[0] == PackageManager.PERMISSION_GRANTED) {
                start()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        stop()
    }

    override fun onResume() {
        super.onResume()
        if (::request.isInitialized) checkPerms()
    }

    private fun stop() {
        if (::hanup.isInitialized) fusedClient.removeLocationUpdates(hanup)
    }

    companion object {
        private const val LOC_CODE = 1231
    }
}