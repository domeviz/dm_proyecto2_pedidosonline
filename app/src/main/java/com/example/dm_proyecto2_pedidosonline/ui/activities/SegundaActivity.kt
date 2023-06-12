package com.example.dm_proyecto2_pedidosonline.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dm_proyecto2_pedidosonline.R
import com.example.dm_proyecto2_pedidosonline.databinding.ActivitySegundaBinding
import com.example.dm_proyecto2_pedidosonline.ui.fragment.FirstFragment
import com.example.dm_proyecto2_pedidosonline.ui.fragment.SecondFragment
import com.example.dm_proyecto2_pedidosonline.ui.fragment.ThirdFragment
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
                    val frag=FirstFragment()
                    val transaction=supportFragmentManager.beginTransaction()
                    //replace: para reemplazar el contenido de ese lugar
                    //add: se agrega informacion encima de la otra
                    //Cada click aqui se agrega a la pila de navegacion
                    transaction.replace(binding.frmContainer.id,frag)
                    //Para que funcione el boton de back del celular
                    //Regresa cada una de las agregaciones a la pila
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }

                R.id.fav -> {
                    // Respond to navigation item 2 click
                    val frag=SecondFragment()
                    val transaction=supportFragmentManager.beginTransaction()
                    transaction.replace(binding.frmContainer.id,frag)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }

                R.id.api -> {
                    // Respond to navigation item 3 click
                    val frag=ThirdFragment()
                    val transaction=supportFragmentManager.beginTransaction()
                    transaction.replace(binding.frmContainer.id,frag)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }

                else -> false
            }
        }
    }
}