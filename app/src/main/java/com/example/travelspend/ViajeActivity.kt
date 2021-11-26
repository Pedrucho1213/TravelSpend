package com.example.travelspend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.travelspend.databinding.ActivityMainBinding
import com.example.travelspend.databinding.ActivityViajeBinding

class ViajeActivity : AppCompatActivity() {
private lateinit var startTravel: ActivityViajeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startTravel = ActivityViajeBinding.inflate(layoutInflater)

        setContentView(startTravel.root)
        supportActionBar?.hide()
        startTravel.backButton.setOnClickListener {
            onBackPressed()
        }

    }
}