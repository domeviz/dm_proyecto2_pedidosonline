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
import com.example.dm_proyecto2_pedidosonline.ui.utilities.FragmentsManager
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
//        Log.d("UCE", "Hola ${name}")
//        binding.textView.text = "Bienvenida " + name.toString()
//        Log.d("UCE", "Entrando a Start")
//        binding.back.setOnClickListener {
//            var intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
        initClass()
    }

    private fun initClass() {
//        binding.back.setOnClickListener {
//            var intent = Intent(
//                this,
//                MainActivity::class.java
//            )
//        }
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.inicio -> {
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        FirstFragment()
                    )
                    true
                }

                R.id.fav -> {
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        SecondFragment()
                    )
                    true
                }

                R.id.api -> {
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        ThirdFragment()
                    )
                    true
                }

                else -> false
            }
        }
        startActivity(intent)
    }
}