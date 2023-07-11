package com.example.dm_proyecto2_pedidosonline.Logic.marvelLogic

import android.util.Log
import com.example.dm_proyecto2_pedidosonline.data.connections.ApiConnection
import com.example.dm_proyecto2_pedidosonline.data.endpoints.MarvelEndpoint
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.MarvelChars
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.data.getMarvelChars

class MarvelLogic {
    suspend fun getAllCharacters(name:String,limit:Int):List<MarvelChars>{

        var itemList= arrayListOf<MarvelChars>()

        var call=
            ApiConnection.getService(ApiConnection.TypeApi.Marvel, MarvelEndpoint::class.java)
        if (call!=null){
            var response=call.getCharactersStartsWith(name,limit)

            if (response.isSuccessful){
                response.body()!!.data.results.forEach{
                    val m=it.getMarvelChars()
                    itemList.add(m)
                }
            }
            else{
                Log.d("UCE",response.toString())
            }
        }
        //Compruebo si la respuesta se ejecuto
        return itemList
    }
    suspend fun getAllMarvelChars(offset:Int,limit:Int):List<MarvelChars>{

        var itemList= arrayListOf<MarvelChars>()

        var call=
            ApiConnection.getService(ApiConnection.TypeApi.Marvel, MarvelEndpoint::class.java)
        if (call!=null){
            var response=call.getAllMarvelChars(offset,limit)

            offset.toString()

            if (response.isSuccessful){
                response.body()!!.data.results.forEach{
                    val m= it.getMarvelChars()
                    itemList.add(m)
                }
            }
            else{
                Log.d("UCE",response.toString())
            }
        }
        return itemList
    }
}