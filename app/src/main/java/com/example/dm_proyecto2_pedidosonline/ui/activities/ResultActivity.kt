package com.example.dm_proyecto2_pedidosonline.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dm_proyecto2_pedidosonline.R
import com.example.dm_proyecto2_pedidosonline.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding:ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.resultOK.setOnClickListener{
            setResult(RESULT_OK)
            //Se cierra la activity y se ejecuta el onDestroy
            finish()
        }
        binding.resultFalse.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}