package com.example.dm_proyecto2_pedidosonline.ui.utilities

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.SettingsClient

//Clase para inyeccion de dependencias
class MyLocationManager(val context: Context) {
    private lateinit var client: SettingsClient

    private fun initVars(){
        if (context!=null) {
            client = LocationServices.getSettingsClient(context!!)
        }
    }
    fun getUserLocation(){
        initVars()
    }
}