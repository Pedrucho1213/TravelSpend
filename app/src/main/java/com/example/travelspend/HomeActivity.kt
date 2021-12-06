package com.example.travelspend

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.travelspend.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    private lateinit var home: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        home = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(home.root)

        home.toolbar.setOnClickListener {
            ShowBottomSheetFragment()
        }
    }

    override fun onResume() {
        super.onResume()

        if (getTravel()){
            showDialog()
        }

    }

   private fun getTravel(): Boolean {
       val admin = DataBase(this, "bd", null, 1)
       val db = admin.writableDatabase
       var data = false
       if (db == null) {
           Toast.makeText(this, "No db", Toast.LENGTH_SHORT).show()
       } else {
           val sql = "select * from viajes"
           val row = db.rawQuery(sql, null)
           row.moveToNext()
           data = if (row.moveToFirst()) {
               val dsc = row.getString(row.getColumnIndexOrThrow("nombre"))
               Log.i(this.toString(), dsc)
               false
           } else {
               Toast.makeText(this, "No existe registros", Toast.LENGTH_SHORT).show()
               db.close()
               row.close()
               true
           }
       }
       return data
    }

    private fun ShowBottomSheetFragment() {
        val mBottomSheetFragment = ModalBottomSheet()
        mBottomSheetFragment.show(supportFragmentManager, "MY_BOTTOM_SHEET")
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
            .setCancelable(false)
            .create()
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.card_cmponent, null)
        builder.setView(dialogView)
        builder.setButton(Dialog.BUTTON_POSITIVE, "Crear viaje") { dialog, _ ->
            StartTravel()
            dialog.dismiss()
        }
        builder.show()
    }

    private fun StartTravel() {
        val intent = Intent(this, ViajeActivity::class.java)
        startActivity(intent)
    }


}

