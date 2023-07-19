package com.example.dm_proyecto2_pedidosonline.ui.activities

import android.content.Intent
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
            val i= Intent()
            i.putExtra("result","Resultado exitoso")
            setResult(RESULT_OK,i)
            //Se cierra la activity y se ejecuta el onDestroy
            finish()
        }
        binding.resultFalse.setOnClickListener{
            val i= Intent()
            i.putExtra("result","Resultado fallido")
            setResult(RESULT_CANCELED,i)
            finish()
        }
    }
}