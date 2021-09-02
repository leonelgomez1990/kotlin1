package com.lfg.botones

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    lateinit var btnRed : Button
    lateinit var btnBlue : Button
    lateinit var btnYellow: Button
    lateinit var btnDelete: Button
    lateinit var txtCartel : TextView
    lateinit var txtMensaje : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Binding
        btnRed = findViewById(R.id.btnRed)
        btnBlue = findViewById(R.id.btnBlue)
        btnYellow = findViewById(R.id.btnYellow)
        btnDelete = findViewById(R.id.btnDelete)
        txtCartel = findViewById(R.id.txtCartel)
        txtMensaje  = findViewById(R.id.txtMensaje)
    }

    private fun printColor(color : Int) : String {
        return getString(R.string.msg_color).plus(" ".plus(getString(color)))
    }

    override fun onStart() {
        super.onStart()

        btnRed.setOnClickListener(){
            txtCartel.text = getString(R.string.color_Red)
            txtCartel.setTextColor(ContextCompat.getColor(this,R.color.red))
            txtMensaje.text = printColor(R.string.color_Red)
        }
        btnYellow.setOnClickListener(){
            txtCartel.text = getString(R.string.color_Yellow)
            txtCartel.setTextColor(ContextCompat.getColor(this,R.color.yellow))
            txtMensaje.text = printColor(R.string.color_Yellow)
        }
        btnBlue.setOnClickListener(){
            txtCartel.text = getString(R.string.color_Blue)
            txtCartel.setTextColor(ContextCompat.getColor(this,R.color.blue))
            txtMensaje.text = printColor(R.string.color_Blue)
        }
        btnDelete.setOnClickListener(){
            txtCartel.text = ""
            txtCartel.setTextColor(ContextCompat.getColor(this,R.color.black))
            txtMensaje.text = getString(R.string.msg_delete)
        }
    }
}