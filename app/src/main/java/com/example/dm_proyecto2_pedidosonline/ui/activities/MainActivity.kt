package com.example.dm_proyecto2_pedidosonline.ui.activities

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dm_proyecto1_foodapp.Logic.validator.LoginValidator
import com.example.dm_proyecto2_pedidosonline.R
import com.example.dm_proyecto2_pedidosonline.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult.*
import androidx.datastore.core.DataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initClass()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    private fun initClass() {
        binding.botonIngresar.setOnClickListener {
            val check= LoginValidator().checkLogin(
                binding.correoEjemplo.text.toString(),
                binding.correoPassword.text.toString()
            )
            if (check){
                lifecycleScope.launch(Dispatchers.IO){
                saveDataStore(binding.correoEjemplo.text.toString())
                }
                var intent = Intent(this,SegundaActivity::class.java
                )
                intent.putExtra("var1",binding.correoEjemplo.text.toString())
                startActivity(intent)
            }
            else {
                Snackbar.make(
                    binding.correoEjemplo , "Usuario y contraseña invalidos",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            }
        binding.btnSearchGoogle.setOnClickListener{
//            val intent=Intent(
//                Intent.ACTION_VIEW,
////                    Uri.parse("http://google.com.ec"))
////                    Uri.parse("http://twitter.com")
//                //Reconoce que es una coordenada y va a google maps
////                    Uri.parse("geo:-0.1953661,-78.4999071")
                    //Llamar
//                Uri.parse("tel:0980427577")
//            )
            val intent=Intent(
                Intent.ACTION_WEB_SEARCH
            )
            intent.setClassName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.googlequicksearchbox.SearchActivity"
            )
            intent.putExtra(SearchManager.QUERY,binding.correoEjemplo.text)
            startActivity(intent)
        }
        val appResultLocal=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            resultActivity->
            //Logica de lo que va a pasar y los posibles resultados
            when(resultActivity.resultCode){
                RESULT_OK->{
                    Log.d("UCE","Resultado Exitoso")
                    Snackbar.make(
                        binding.titulo,"Resultado Exitoso",Snackbar.LENGTH_LONG
                    ).show()
                }
                RESULT_CANCELED->{
                    Log.d("UCE","Resultado Fallido")
                    Snackbar.make(
                        binding.titulo,"Resultado Fallido",Snackbar.LENGTH_LONG
                    ).show()
                }
                else->{
                    Log.d("UCE","Resultado Dudoso")
                }
            }
            //Mejor se hace con when
//            if(resultActivity.resultCode== RESULT_OK){
//
//            } else{
//                if(resultActivity.resultCode== RESULT_CANCELED){
//
//                }else{
//
//                }
//            }
        }
        //Las 2 activitys se van a comunicar y en base a eso se determina el appResultLocal
        binding.btnResult.setOnClickListener{
            val resIntent=Intent(this,ResultActivity::class.java)
            appResultLocal.launch(resIntent)
        }
    }

    private suspend fun saveDataStore(stringData:String){
        dataStore.edit{prefs->
            prefs[stringPreferencesKey("usuario")]=stringData
        }
    }
}