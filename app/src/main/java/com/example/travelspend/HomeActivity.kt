package com.example.travelspend

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.travelspend.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_home)
        Toolbar.setOnClickListener() {
            ShowBottomSheetFragment()

        }

        consulta.setOnClickListener {
            consultar()
        }

    }
    private fun consultar(){
        val intent2 = Intent(this, consultardatos::class.java)
        startActivity(intent2)
    }
    fun ShowBottomSheetFragment() {
        val mBottomSheetFragment = ModalBottomSheet()
        mBottomSheetFragment.show(supportFragmentManager, "MY_BOTTOM_SHEET")
    }
   override fun onResume() {
        super.onResume()
        showDialog()
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
        val intent = Intent(this, ViajeActivity::class.java)
        startActivity(intent)
    }
}

