package com.example.dm_proyecto2_pedidosonline.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    val userLiveData: MutableLiveData<String> = MutableLiveData()
}