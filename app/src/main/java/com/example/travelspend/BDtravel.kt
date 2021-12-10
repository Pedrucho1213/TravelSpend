package com.example.travelspend

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class DataBase (context : Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int): SQLiteOpenHelper(context, name, factory, version){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table viajes (codigo INTEGER PRIMARY KEY AUTOINCREMENT, nombre text, presupuesto double, fechai text, fechaf text, descrip taxt )")
        db?.execSQL("create table gastos (codigo INTEGER PRIMARY KEY AUTOINCREMENT, monto double, moneda text, concepto text, fecha text,mpago text, descrip taxt )")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("Drop table if exists gastos")
        db?.execSQL("Drop table if exists viajes")
    }
}