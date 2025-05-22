package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button

class Hub : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hub)

        val Player = findViewById<Button>(R.id.Player)
        val Calculator = findViewById<Button>(R.id.Calculator)
        val GPS = findViewById<Button>(R.id.GPS)

        GPS.setOnClickListener {
            startActivity(Intent(this, gps::class.java))
        }

        Player.setOnClickListener {
            startActivity(Intent(this, AudioPlayer::class.java))
        }

        Calculator.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}

