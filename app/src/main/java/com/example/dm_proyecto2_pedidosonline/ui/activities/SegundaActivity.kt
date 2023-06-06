package com.example.dm_proyecto2_pedidosonline.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dm_proyecto2_pedidosonline.R
import com.example.dm_proyecto2_pedidosonline.databinding.ActivitySegundaBinding

class SegundaActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySegundaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySegundaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        var name:String=" "
        intent.extras.let{
            name=it?.getString("var1")!!
        }
        Log.d("UCE","Hola ${name}")
        binding.textView.text="Bienvenida "+name.toString()
        Log.d("UCE","Entrando a Start")
        binding.back.setOnClickListener{
            var intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        initClass()
    }

    private fun initClass() {
        binding.back.setOnClickListener {
            var intent = Intent(
                this,
                MainActivity::class.java
            )
            startActivity(intent)
        }
    }
}