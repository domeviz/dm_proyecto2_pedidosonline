package com.example.dm_proyecto2_pedidosonline.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class BiometricViewModel: ViewModel() {
    val isLoading = MutableLiveData<Boolean>()

    suspend fun chargingData(){
        isLoading.postValue(true)
        delay(5000)
        isLoading.postValue(false)
    }
}