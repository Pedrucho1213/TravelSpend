package com.example.travelspend

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_home)
        Toolbar.setOnClickListener() {
            ShowBottomSheetFragment()
        }
    }


   override fun onResume() {
        super.onResume()
        showDialog()
    }

    private fun ShowBottomSheetFragment() {
        val mBottomSheetFragment = ModalBottomSheet()
        mBottomSheetFragment.show(supportFragmentManager, "MY_BOTTOM_SHEET")
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
             .setCancelable(true)
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

    private fun StartTravel(){
        val intent = Intent(this, AddTravel::class.java)
        startActivity(intent)
    }
}

