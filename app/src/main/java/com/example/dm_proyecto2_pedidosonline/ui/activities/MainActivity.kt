package com.example.dm_proyecto2_pedidosonline.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.speech.RecognizerIntent
import com.example.dm_proyecto1_foodapp.Logic.validator.LoginValidator
import com.example.dm_proyecto2_pedidosonline.R
import com.example.dm_proyecto2_pedidosonline.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.PermissionChecker.PermissionResult
import androidx.datastore.core.DataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.dm_proyecto2_pedidosonline.ui.utilities.MyLocationManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import java.util.Locale
import java.util.UUID

// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //Ubicacion y GPS
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var client:SettingsClient
    private lateinit var locationSettingRequest:LocationSettingsRequest

    private var currentLocation: Location? = null

    @SuppressLint("MissingPermission")
    private val locationContract =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when (isGranted) {
                true -> {
                    client.checkLocationSettings(locationSettingRequest).addOnSuccessListener {
                        val task= fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                            fusedLocationProviderClient.requestLocationUpdates(
                                locationRequest,
                                locationCallback,
                                Looper.getMainLooper())
                        }


                    }
                    val task = fusedLocationProviderClient.lastLocation
                    task.addOnSuccessListener { location ->
                        fusedLocationProviderClient.requestLocationUpdates(
                            locationRequest,
                            locationCallback,
                            Looper.getMainLooper()
                        )
                    }
                    task.addOnFailureListener { ex ->
                        if (ex is ResolvableApiException) {
                            ex.startResolutionForResult(
                                this@MainActivity,
                                LocationSettingsStatusCodes.RESOLUTION_REQUIRED
                            )
                        }
                    }

                    /*
//                  Para abrir la configuracion de Ubicacion
                    val task = fusedLocationProviderClient.lastLocation
                    val alert=AlertDialog.Builder(this).apply {
                        setTitle("Notificación")
                        setMessage("Por favor verifique que el GPS esta activo")
                        setPositiveButton("Verificar"){
                            dialog,id->
                            //Aqui podemos generar cualquier cosa en el dialogo
                            //startActivity sirve para lanzar un Intent
                            //Este Intent es usado para comunicar las Activity
                            val i=Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                            //Activa la ubicacion
                            startActivity(i)
                            dialog.dismiss()
                        }
                        setCancelable(false)
                    }.show()
                     */
                }


                shouldShowRequestPermissionRationale(
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) -> {
                }

                false -> {
                    Snackbar.make(binding.titulo, "Permiso denegado", Snackbar.LENGTH_LONG).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        client=LocationServices.getSettingsClient(this)
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
//        .setMaxUpdates(3)
            .build()
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                if (locationResult != null) {
                    locationResult.locations.forEach { location ->
                        currentLocation = location
                        Log.d(
                            "UCE", "Ubicacion: ${location.latitude}, "
                                    + "${location.longitude}"
                        )
                    }
                    return
                } else {
                    Log.d("UCE","BOTON APAGADO")
                }
            }
        }
                client=LocationServices.getSettingsClient(this)
                locationSettingRequest=LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest).build()

    }

    override fun onStart() {
        super.onStart()
        initClass()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

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

    //Inyeccion de dependencias
    private fun test(){
        var location=MyLocationManager(this)
        location.getUserLocation()

    }
}