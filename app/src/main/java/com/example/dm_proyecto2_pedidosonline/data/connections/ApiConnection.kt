package com.example.dm_proyecto2_pedidosonline.data.connections

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConnection {
    enum class TypeApi{
        Jikan, Marvel, Pets
    }
    private val API_JIKAN="https://api.jikan.moe/v4/"
    private val API_MARVEL="https://gateway.marvel.com/v1/public/"
    private val API_PETS="https://gateway.marvel.com/v1/public/"
    private fun getConnection(base:String):Retrofit{
        var retrofit = Retrofit.Builder()
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
    suspend fun <T,E :Enum<E>> getService(typeApi: E, service:Class<T>):T{
        var BASE=" "
        when(typeApi.name){
            TypeApi.Jikan.name->{
                BASE= API_JIKAN
            }
            TypeApi.Marvel.name->{
                BASE= API_MARVEL
            }
        }
        return getConnection(BASE).create(service)
    }
    fun getJikanConnection():Retrofit{
        //Construye el objeto Retrofit que ses conecta con la url base
        var retrofit = Retrofit.Builder()
            .baseUrl("https://api.jikan.moe/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

}