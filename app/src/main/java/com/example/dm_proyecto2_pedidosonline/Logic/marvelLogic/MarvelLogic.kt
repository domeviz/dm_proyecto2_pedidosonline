package com.example.dm_proyecto2_pedidosonline.Logic.marvelLogic

import com.example.dm_proyecto2_pedidosonline.data.connections.ApiConnection
import com.example.dm_proyecto2_pedidosonline.data.endpoints.MarvelEndPoint
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.characters.MarvelChars

class MarvelLogic {
    suspend fun getCharactersStartsWith(name:String,limit:Int):List<MarvelChars>{

        var itemList= arrayListOf<MarvelChars>()

        var response=
            ApiConnection.getService(
                ApiConnection.TypeApi.Marvel,
                MarvelEndPoint::class.java
            ).getCharactersStartsWith(name,limit)


        if (response.isSuccessful){
            response.body()!!.data.results.forEach{
                var comic:String="No avaible"
                if(it.comics.items.size>0){
                    comic=it.comics.items[0].name
                }
                val m= MarvelChars(
                    it.id,
                    it.name,
                    comic,
                    it.thumbnail.path+"."+it.thumbnail.extension
                )
                itemList.add(m)
            }
        }


        //Compruebo si la respuesta se ejecuto

        return itemList
    }
}