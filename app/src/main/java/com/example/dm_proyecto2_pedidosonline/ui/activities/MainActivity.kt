package com.example.dm_proyecto2_pedidosonline.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import com.example.dm_proyecto1_foodapp.Logic.validator.LoginValidator
import com.example.dm_proyecto2_pedidosonline.R
import com.example.dm_proyecto2_pedidosonline.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult.*
import androidx.annotation.RequiresApi
import androidx.core.content.PermissionChecker.PermissionResult
import androidx.datastore.core.DataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale
import java.util.UUID

// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

//Interfaz que nos permite
private lateinit var fusedLocationProviderClient:FusedLocationProviderClient

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)

    }

    override fun onStart() {
        super.onStart()
        initClass()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @SuppressLint("MissingPermission")
    private fun initClass() {
        binding.botonIngresar.setOnClickListener {
            val check = LoginValidator().checkLogin(
                binding.correoEjemplo.text.toString(),
                binding.correoPassword.text.toString()
            )
            if (check) {
                lifecycleScope.launch(Dispatchers.IO) {
                    saveDataStore(binding.correoEjemplo.text.toString())
                }
                var intent = Intent(
                    this, SegundaActivity::class.java
                )
                try {
                    startActivity(intent)
                } catch (e: Exception) {
                    Log.d("UCE", "Falló")
                }
            } else {
                Snackbar.make(
                    binding.correoEjemplo, "Usuario y contraseña invalidos",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        //Aqui va la solicitud del permiso
        var locationContract =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                    isGranted ->
                when (isGranted) {
                    true -> {
                        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                            it.longitude
                            it.latitude
                            //Siguiente clase
                            val a = Geocoder(this)
                            a.getFromLocation(it.latitude, it.longitude, 1)
                            Snackbar.make(binding.titulo,"Permiso concedido: ${it.latitude}, ${it.longitude}",Snackbar.LENGTH_LONG).show()
                        }

//                        a.getFromLocation(it.longitude)
//                        //Tipo de task location
//                        //Obtengo la tarea
//                        val task = fusedLocationProviderClient.lastLocation
//
//                        task.addOnSuccessListener {
//                            if (task.result != null) {
//                                Snackbar.make(
//                                    binding.titulo,
//                                    //Me da una ubicacion exacta
//                                    "${it.latitude}, ${it.longitude}",
//                                    Snackbar.LENGTH_LONG
//                                ).show()
//                            } else {
//                                Snackbar.make(
//                                    binding.titulo,
//                                    "Encienda el GPS por favor",
//                                    Snackbar.LENGTH_LONG
//                                ).show()
//                            }
//                        }
//                    Snackbar.make(binding.titulo,"Permiso concedido",Snackbar.LENGTH_LONG).show()
                    }

                        shouldShowRequestPermissionRationale(
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )-> {

                        }
                        false -> {
                            Snackbar.make(binding.titulo,"Permiso denegado",Snackbar.LENGTH_LONG).show()
                        }
    }
}
        binding.btnSearchGoogle.setOnClickListener {

            //Se importa el Android general
            locationContract.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)

//            val intent = Intent(
//                Intent.ACTION_WEB_SEARCH
//            )
//            intent.setClassName(
//                "com.google.android.googlequicksearchbox",
//                "com.google.android.googlequicksearchbox.SearchActivity"
//            )
//            intent.putExtra(
//                SearchManager.QUERY,
//                binding.correoEjemplo.text.toString()
//            )
//            startActivity(intent)

        }
        val appResultLocal =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultActivity ->
                val sn = Snackbar.make(binding.titulo, "", Snackbar.LENGTH_LONG)
                //Logica de lo que va a pasar y los posibles resultados
                var message = " "
                when (resultActivity.resultCode) {
                    RESULT_OK -> {
                        message = resultActivity.data?.getStringExtra("result").orEmpty()
                        sn.setBackgroundTint(
                            resources.getColor(
                                R.color.rosa2
                            )
                        )
                    }

                    RESULT_CANCELED -> {
                        message = "Resultado fallido"
                        sn.setBackgroundTint(
                            resources.getColor(
                                R.color.rosa2
                            )
                        )
                    }

                    else -> {
                        "Resultado Dudoso"
                    }
                }
                sn.setText(message)
                sn.show()

            }
        val speechToText =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                val sn = Snackbar.make(binding.titulo, "", Snackbar.LENGTH_LONG)
                var message = ""
                when (activityResult.resultCode) {
                    RESULT_OK -> {
                        message = "Proceso Exitoso"
                        sn.setBackgroundTint(
                            resources.getColor(
                                R.color.rosa2
                            )
                        )

                        val msg =
                            activityResult.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                                ?.get(0).toString()
                        if (msg.isNotEmpty()) {
                            val intent = Intent(
                                Intent.ACTION_WEB_SEARCH
                            )
                            intent.setClassName(
                                "com.google.android.googlequicksearchbox",
                                "com.google.android.googlequicksearchbox.SearchActivity"
                            )
                            intent.putExtra(
                                SearchManager.QUERY, msg.toString()
                            )
                            startActivity(intent)
                        }

                    }

                    RESULT_CANCELED -> {
                        message = "Proceso Cancelado"
                        sn.setBackgroundTint(
                            resources.getColor(
                                R.color.rosa
                            )
                        )
                    }

                    else -> {
                        message = "Proceso Dudoso"
                        sn.setBackgroundTint(
                            resources.getColor(
                                R.color.rosa
                            )
                        )
                    }
                }
                sn.setText(message)
                sn.show()

            }

        //Las 2 activitys se van a comunicar y en base a eso se determina el appResultLocal
        binding.btnResult.setOnClickListener {
            val intentSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_PROMPT,
                "Di algo"
            )
            speechToText.launch(intentSpeech)
        }
    }

    private suspend fun saveDataStore(stringData: String) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey("usuario")] = stringData
            prefs[stringPreferencesKey("session")] = UUID.randomUUID().toString()
            prefs[stringPreferencesKey("email")] = "Dome"
        }
    }
}
