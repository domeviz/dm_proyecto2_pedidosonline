package com.example.dm_proyecto2_pedidosonline.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dm_proyecto2_pedidosonline.R
import com.example.dm_proyecto2_pedidosonline.databinding.ActivitySegundaBinding
import com.google.android.material.snackbar.Snackbar

class SegundaActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySegundaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySegundaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        var name: String = " "
//        intent.extras.let{
//            name=it?.getString("var1")!!
//        }
        Log.d("UCE", "Hola ${name}")
        binding.textView.text = "Bienvenida " + name.toString()
        Log.d("UCE", "Entrando a Start")
        binding.back.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
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
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.inicio -> {
                    // Respond to navigation item 1 click
                    Snackbar.make(
                        binding.bottomNavigation, "Holi",
                        Snackbar.LENGTH_LONG
                    ).show()
                    true
                }

                R.id.fav -> {
                    // Respond to navigation item 2 click
                    var suma:Int=0
                    for(i in listOf(9,2,3)){
                        suma+=i
                    }
                    Snackbar.make(
                        binding.bottomNavigation, "La suma es ${suma}",
                        Snackbar.LENGTH_LONG
                    ).show()
                    true
                }

                R.id.api -> {
                    // Respond to navigation item 3 click
                    true
                }

                else -> false
            }
        }
    }
}