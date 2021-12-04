package com.example.travelspend

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.travelspend.databinding.ActivityConsultardatosBinding



class consultardatos : AppCompatActivity() {
    private lateinit var binding: ActivityConsultardatosBinding
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultardatosBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.consulta.setOnClickListener {
            val admin = DataBase(this, "bd", null, 1)
            val db = admin.writableDatabase
            if (db == null) {
                Toast.makeText(this, "No db", Toast.LENGTH_SHORT).show()
            } else {
                val sql = "select * from viajes"
                val row = db.rawQuery(sql, null)
                row.moveToNext()
                if (row.moveToFirst()) {
                    val dsc = row.getString(row.getColumnIndex("nombre"))
                    Log.i(this.toString(),"${dsc}")
                } else {
                    Toast.makeText(this, "No existe registros", Toast.LENGTH_SHORT).show()
                    db.close()
                    row.close()
                }
            }
        }
    }
}