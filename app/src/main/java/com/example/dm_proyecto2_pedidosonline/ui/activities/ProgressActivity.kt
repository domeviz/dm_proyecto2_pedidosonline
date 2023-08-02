package com.example.dm_proyecto2_pedidosonline.ui.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.dm_proyecto2_pedidosonline.R
import com.example.dm_proyecto2_pedidosonline.databinding.ActivityProgressBinding
import com.example.dm_proyecto2_pedidosonline.ui.viewmodels.ProgressViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressActivity : AppCompatActivity() {

    private lateinit var binding:ActivityProgressBinding
    private  val progressviewmodel by viewModels<ProgressViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressviewmodel.progressState.observe(this,{
            binding.progressBar1.visibility=it
        })

        progressviewmodel.items.observe(this,{
            Toast.makeText(this,it[10].nombre,Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,NotificationActivity::class.java))
        })

        //Listeners
        binding.btnProceso.setOnClickListener{
            progressviewmodel.processBackground(3000)
        }

        binding.btnProceso1.setOnClickListener{
            //Corrutinas
            lifecycleScope.launch(Dispatchers.IO){
                progressviewmodel.getMarvelChars(0,90)
            }
        }
    }
}