package com.example.travelspend

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import org.greenrobot.greendao.database.Database

val cves = ArrayList<String>()

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
        }else{
            getexpense()
            deleteExpense()
            getDataFromList()
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

    private fun getexpense() {
        val items = ArrayList<String>()
        val manager = DataBase(this, "bd", null, 1)
        val db = manager.writableDatabase
        if (db == null) {
            Toast.makeText(this, "No db", Toast.LENGTH_SHORT).show()
        } else {
            val sql = "select * from gastos"
            val row = db.rawQuery(sql, null)
            cves.clear()

            while (row.moveToNext()) {
                val cdo = row.getString(row.getColumnIndexOrThrow("codigo"))
                val mto = row.getString(row.getColumnIndexOrThrow("monto"))
                val mnd = row.getString(row.getColumnIndexOrThrow("moneda"))
                val cpt = row.getString(row.getColumnIndexOrThrow("concepto"))
                val fch = row.getString(row.getColumnIndexOrThrow("fecha"))
                val mpg = row.getString(row.getColumnIndexOrThrow("mpago"))
                val dsc = row.getString(row.getColumnIndexOrThrow("descrip"))
                Log.i("dATA", "$cdo $mto $mnd $cpt $fch $mpg $dsc")

                items.add("Concepto: $cpt - Monto: $mto $mnd")
                cves.add(cdo)
            }

            val adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, items)
            home.listGastos.adapter = adapter

            db.close()
            row.close()

        }
    }

    private fun deleteExpense(){
        home.listGastos.onItemLongClickListener = AdapterView.OnItemLongClickListener { adapterView, view, i, l ->
            val modal = AlertDialog.Builder(this)
            modal.setTitle("Eliminar gasto")
            modal.setMessage("¿ Seguro que desea eliminar este gasto ${cves[i]}?")
            modal.setPositiveButton("si"){ dialogInterface, j ->
                deleteItem(cves[i].toString())
            }
            modal.setNegativeButton("No"){dialogInterface, i ->
                dialogInterface.dismiss()
            }
            modal.show()
            true
        }
    }

    private fun deleteItem(id:String) {
        val manager = DataBase(this, "bd", null, 1)
        val db = manager.writableDatabase
        val cnt = db.delete("gastos", "codigo =?", arrayOf(id))
        db.close()
        if (cnt == 1){
            Toast.makeText(this, "Se borro el registro satisfactoriamente", Toast.LENGTH_SHORT).show()
            getexpense()
        }else{
            Toast.makeText(this, "Error al borrar el registro", Toast.LENGTH_SHORT).show()

        }
    }

    private fun getDataFromList(){
        home.listGastos.onItemClickListener = AdapterView.OnItemClickListener{adapterView, view, i, l ->
            val intent = Intent(this, AddExpense::class.java)
            intent.putExtra("id", cves[i])
            startActivity(intent)
        }
    }

    private fun logout() {
        val authParams =
            HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM).createParams()
        val service = HuaweiIdAuthManager.getService(this, authParams)
        service.cancelAuthorization().addOnCompleteListener {
            if (it.isSuccessful) {
                // Processing after a successful authorization revoking.
                startActivity(Intent(this, MainActivity::class.java))
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

