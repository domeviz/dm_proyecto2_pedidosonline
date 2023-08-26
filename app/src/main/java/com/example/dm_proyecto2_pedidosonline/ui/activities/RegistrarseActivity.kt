package com.example.dm_proyecto2_pedidosonline.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.dm_proyecto2_pedidosonline.R
import com.example.dm_proyecto2_pedidosonline.databinding.ActivityRegistrarseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistrarseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarseBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegistrarseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.botonIngresar.setOnClickListener {
            authWithFirebaseEmail(
                binding.correoEjemplo.text.toString(),
                binding.correoPassword.text.toString()
            )
        }
        binding.spider.setOnClickListener {
            startActivity(Intent(this,CameraActivity::class.java))
        }
        binding.ironman.setOnClickListener {
            startActivity(Intent(this,ProgressActivity::class.java))
        }
    }

    private fun authWithFirebaseEmail(email:String,password:String){
        try{
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Constants.TAG, "createUserWithEmail:success")

                    Toast.makeText(
                        baseContext,
                        "Autenticación exitosa",
                        Toast.LENGTH_SHORT,
                    ).show()

                    val user = auth.currentUser

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Constants.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Autenticación fallida",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(
                baseContext,
                "Acceso failed.",
                Toast.LENGTH_SHORT,
            ).show()
        }
    }
}