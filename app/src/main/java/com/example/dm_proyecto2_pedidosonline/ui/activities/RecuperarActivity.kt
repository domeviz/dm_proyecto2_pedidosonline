package com.example.dm_proyecto2_pedidosonline.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.dm_proyecto2_pedidosonline.R
import com.example.dm_proyecto2_pedidosonline.databinding.ActivityRecuperarBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecuperarActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRecuperarBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRecuperarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.botonRecuperar.setOnClickListener {
            recoveryPasswordWithEmail(
                binding.correoEjemplo.text.toString()
            )
        }
        binding.drstrange.setOnClickListener {
            startActivity(Intent(this,ResultActivity::class.java))
        }
    }

    private fun recoveryPasswordWithEmail(email :  String){
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    Toast.makeText(
                        baseContext,
                        "Correo de verificación enviado",
                        Toast.LENGTH_SHORT,
                    ).show()

                    MaterialAlertDialogBuilder(this).apply {
                        setTitle("Alert")
                        setMessage("Correo de recuperación enviado correctamente")
                        setCancelable(true)
                    }.show()
                }else {
                    Log.w(Constants.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "No existe el correo.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }

            }
    }
}