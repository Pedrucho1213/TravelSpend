package com.example.travelspend

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.travelspend.databinding.ActivityTravelBinding

class AddTravel : AppCompatActivity() {
    private lateinit var startTravel: ActivityTravelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startTravel = ActivityTravelBinding.inflate(layoutInflater)

        setContentView(startTravel.root)
        supportActionBar?.hide()

        startTravel.backButton.setOnClickListener {
            onBackPressed()
        }

        startTravel.addViaje.setOnClickListener {
            saveTravel()
            onBackPressed()
        }
    }

    private fun showDatePicker(){
        
    }


    private fun saveTravel() {
        val admin = DataBase(this, "bd", null, 1)
        val db = admin.writableDatabase
        val record = ContentValues()
        record.put("nombre", startTravel.editnombre.text.toString())
        record.put("presupuesto", startTravel.editpresupuesto.text.toString().toDouble())
        record.put("fechai", startTravel.editTextDate.text.toString())
        record.put("fechaf", startTravel.editTextDate2.text.toString())
        record.put("descrip", startTravel.editdescripcion.text.toString())
        db.insert("viajes", null, record)
        db.close()
        startTravel.editnombre.text = null
        startTravel.editpresupuesto.text = null
        startTravel.editTextDate.text = null
        startTravel.editTextDate2.text = null
        startTravel.editdescripcion.text = null
        record.clear()
        Toast.makeText(this, "Tu viaje ha sido agregado", Toast.LENGTH_SHORT).show()
    }
}