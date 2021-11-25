package com.example.travelspend

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.travelspend.databinding.CardCmponentBinding

class CardComponent : AppCompatActivity(){

    private lateinit var card: CardCmponentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        card = CardCmponentBinding.inflate(layoutInflater)
        setContentView(card.root)
    }

    private fun createTravel(){
        val intent = Intent(this, Create)
    }

}