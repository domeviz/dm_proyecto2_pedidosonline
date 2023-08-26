package com.example.dm_proyecto1_foodapp.Logic.validator

import com.example.dm_proyecto1_foodapp.data.entities.LoginUser

class LoginValidator {
    fun checkLogin(name:String ,password:String):Boolean{
        val admin= LoginUser()
        return(admin.name==name
                && admin.pass==password)
    }

}