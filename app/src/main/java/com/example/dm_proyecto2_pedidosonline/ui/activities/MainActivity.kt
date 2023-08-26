package com.example.dm_proyecto2_pedidosonline.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.speech.RecognizerIntent
import com.example.dm_proyecto2_pedidosonline.R
import com.example.dm_proyecto2_pedidosonline.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.dm_proyecto2_pedidosonline.ui.utilities.MyLocationManager
import com.example.dm_proyecto2_pedidosonline.ui.viewmodels.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.UUID
enum class ProviderType{
    GOOGLE
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private val GOOGLE_SIGN_IN= 100
    private lateinit var binding: ActivityMainBinding
    //Ubicacion y GPS
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var client:SettingsClient
    private lateinit var locationSettingRequest:LocationSettingsRequest
    private lateinit var auth: FirebaseAuth

    private var currentLocation: Location? = null

    private lateinit var userViewModel: UserViewModel

    @SuppressLint("MissingPermission")
    val locationContract =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when (isGranted) {
                true -> {

                    client.checkLocationSettings(locationSettingRequest).apply {

                        addOnSuccessListener {
                            val task = fusedLocationProviderClient.lastLocation
                            task.addOnSuccessListener {
                                    location->

                                fusedLocationProviderClient.requestLocationUpdates(
                                    locationRequest,
                                    locationCallback,
                                    Looper.getMainLooper()
                                )
                            }
                        }
                        addOnFailureListener{ex->

                            if (ex is ResolvableApiException){
                                //  startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                                ex.startResolutionForResult(
                                    this@MainActivity,
                                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED
                                )
                            }

                        }


                    }

                }
                shouldShowRequestPermissionRationale(
                    Manifest.permission.ACCESS_FINE_LOCATION) -> {
                }

                false -> {
                    Snackbar.make(binding.titulo,"Permiso denegado",Snackbar.LENGTH_LONG).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val prefs =getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
        prefs.apply()

        binding.google.setOnClickListener {
            val googleConf=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build()

            val googleClient= GoogleSignIn.getClient(this,googleConf)
            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }

        binding.witch.setOnClickListener{
            startActivity(Intent(this,NotificationActivity::class.java))
        }

        binding.marvel.setOnClickListener {
            startActivity(Intent(this,BiometricActivity::class.java))
        }

        binding.botonIngresar.setOnClickListener {
            signWithFirebaseEmail(
                binding.correoEjemplo.text.toString(),
                binding.correoPassword.text.toString()
            )
        }

        binding.registrarse.setOnClickListener {
            startActivity(Intent(this,RegistrarseActivity::class.java))
        }

        binding.olvidoPassword.setOnClickListener {
            startActivity(Intent(this,RecuperarActivity::class.java))
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

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
                }
            }
        }
                client=LocationServices.getSettingsClient(this)
                locationSettingRequest=LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest).build()

    }

    private fun signWithFirebaseEmail(email:String, password:String) {
        try{
        auth.signInWithEmailAndPassword (email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Constants.TAG, "singInrWithEmail:success")
                    val user =auth.currentUser
                    Toast.makeText(
                        baseContext,
                        "Authentication success.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    val intent = Intent(this, SegundaActivity::class.java)
                    intent.putExtra("user", email)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Constants.TAG, "singInrWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
        } catch (e: Exception) {
            Toast.makeText(
                baseContext,
                "Ingresa las credenciales",
                Toast.LENGTH_SHORT,
            ).show()
        }


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

    @SuppressLint("MissingPermission")
    private fun initClass() {

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
//        binding.btnResult.setOnClickListener {
//            val intentSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//            intentSpeech.putExtra(
//                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
//            )
//            intentSpeech.putExtra(
//                RecognizerIntent.EXTRA_LANGUAGE,
//                Locale.getDefault()
//            )
//            intentSpeech.putExtra(
//                RecognizerIntent.EXTRA_PROMPT,
//                "Di algo"
//            )
//            speechToText.launch(intentSpeech)
//        }
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
    
    //Google
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {
            // Manejar la respuesta de One Tap aquí
            val task=GoogleSignIn.getSignedInAccountFromIntent(data)
            val account=task.getResult(ApiException::class.java)
            if(account!=null){
                val credential= GoogleAuthProvider.getCredential(account.idToken,null)
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{
                    if(it.isSuccessful){
                        val user = FirebaseAuth.getInstance().currentUser
                        val email = user?.email
                        // Establecer el correo electrónico en el UserViewModel
                        userViewModel.userLiveData.value = email
                        Toast.makeText(
                            baseContext,
                            "Autenticación exitosa",
                            Toast.LENGTH_SHORT,
                        ).show()
                        val intent = Intent(this, SegundaActivity::class.java)
                        intent.putExtra("user", email)
                        startActivity(intent)
                    }else{
                        Toast.makeText(
                            baseContext,
                            "No se pudo auteticar",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            }

        }
    }
}