package com.example.travelspend

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.travelspend.MainActivity.Companion.TAG
import com.example.travelspend.databinding.ActivityHomeBinding
import com.example.travelspend.databinding.SheetbottomBinding
import com.huawei.hms.common.ApiException
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper


class HomeActivity : AppCompatActivity() {

    private lateinit var home: ActivityHomeBinding
    private lateinit var sheetBotom: SheetbottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        home = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(home.root)
        sheetBotom = SheetbottomBinding.inflate(layoutInflater)

        home.toolbar.setOnClickListener { ShowBottomSheetFragment() }

        home.addGasto.setOnClickListener { goToAddGasto() }

        sheetBotom.CloseSession.setOnClickListener { logout() }

    }

    override fun onResume() {
        super.onResume()

        if (getTravel()) {
            showDialog()
        }

    }

    private fun goToAddGasto() {
        val intent = Intent(this, AddExpense::class.java)
        startActivity(intent)
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
        val intent = Intent(this, AddTravel::class.java)
        startActivity(intent)
    }

    private fun logout(){
        val authParams = HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM).createParams()
        val service = HuaweiIdAuthManager.getService(this, authParams)
        service.cancelAuthorization().addOnCompleteListener{
            if (it.isSuccessful) {
                // Processing after a successful authorization revoking.
                startActivity(Intent(this,MainActivity::class.java))
            } else {
                // Handle the exception.
                val exception = it.exception
                if (exception is ApiException) {
                    val statusCode = exception.statusCode
                    Log.e(TAG, "onFailure: $statusCode")
                }
            }
        }
    }
}

