package com.example.travelspend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.travelspend.databinding.ActivityAddExpenseBinding

class AddExpense : AppCompatActivity() {

    private lateinit var addGasto: ActivityAddExpenseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addGasto = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(addGasto.root)

        addGasto.backButton.setOnClickListener { goBack() }

    }

    private fun goBack(){
        onBackPressed()
    }
}