package com.example.travelspend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.card_cmponent.*


class HomeActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        showDialog()
    }


    private fun showDialog() {
        val customDialog = AlertDialog.Builder(this)
            .setCancelable(true)
            .create()
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.card_cmponent, null)
        customDialog.setView(dialogView)
        customDialog.show()
    }
}

