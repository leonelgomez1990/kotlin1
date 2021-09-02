package com.lfg.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var txtCartel : TextView
    lateinit var btnShow : Button
    var textoCartel : String = "Hola Leo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtCartel = findViewById(R.id.txtShow)
        btnShow = findViewById(R.id.btnShow)
    }

    override fun onStart() {
        super.onStart()
        btnShow.setOnClickListener()
        {
            txtCartel.text = textoCartel
        }
    }
}