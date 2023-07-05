package com.example.dm_proyecto2_pedidosonline.data.connections

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConnection {

    fun getJikanConnection(): Retrofit {
        //Construye el objeto Retrofit que se conecta con la url base
        var retrofit = Retrofit.Builder()
            .baseUrl("https://api.jikan.moe/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
}