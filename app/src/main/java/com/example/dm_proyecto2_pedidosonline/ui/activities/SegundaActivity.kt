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
        initClass()
    }

    private fun initClass() {
        val user = intent.getStringExtra("user")
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val bundle = Bundle()
            bundle.putString("user", user)
            when (item.itemId) {
                R.id.inicio -> {
                    val firstFragment = FirstFragment()
                    firstFragment.arguments = bundle

                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        firstFragment
                    )
                    true
                }

                R.id.fav -> {
                    val secondFragment = SecondFragment()
                    secondFragment.arguments = bundle
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        secondFragment
                    )
                    true
                }

                R.id.api -> {
                    val thirdFragment = ThirdFragment()
                    thirdFragment.arguments = bundle
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        thirdFragment
                    )
                    true
                }

                else -> false
            }
        }
        //No funcionaba porque empezaba la activity otra vez
//        startActivity(intent)
    }
}