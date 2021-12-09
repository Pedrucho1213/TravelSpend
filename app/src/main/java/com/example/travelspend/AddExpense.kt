package com.example.travelspend

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.travelspend.databinding.ActivityAddExpenseBinding

class AddExpense : AppCompatActivity() {

    private lateinit var addGasto: ActivityAddExpenseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addGasto = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(addGasto.root)

        addGasto.backButton.setOnClickListener { goBack() }


        addGasto.addexpense.setOnClickListener {
        saveExpense()
        }
    }

    private fun goBack(){
        onBackPressed()
    }

    private fun saveExpense() {
        val admin = DataBase(this, "bd", null, 1)
        val db = admin.writableDatabase
        val record = ContentValues()
        record.put("monto", addGasto.txtMoneyDepense.text.toString().toDouble())
        record.put("moneda", addGasto.txtMoneyType.text.toString())
        record.put("concepto",addGasto.txtConceptDepense.text.toString()  )
        record.put("fecha", addGasto.txtDateDepense.text.toString() )
        record.put("mpago", addGasto.txtModePayment.text.toString() )
        record.put("descrip", addGasto.txtDescriptionDepense.text.toString() )
        db.insert("gastos", null, record)
        db.close()
        addGasto.txtMoneyDepense.text = null
        addGasto.txtMoneyType.text = null
        addGasto.txtConceptDepense.text = null
        addGasto.txtDateDepense.text = null
        addGasto.txtModePayment.text = null
        addGasto.txtDescriptionDepense.text = null

        record.clear()
        Toast.makeText(this, "El gasto ha sido agregado", Toast.LENGTH_SHORT).show()
    }

}