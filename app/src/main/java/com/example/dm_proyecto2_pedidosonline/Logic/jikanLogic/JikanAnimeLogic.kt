package com.example.dm_proyecto2_pedidosonline.Logic.jikanLogic

import com.example.dm_proyecto2_pedidosonline.data.connections.ApiConnection
import com.example.dm_proyecto2_pedidosonline.data.endpoints.JikanEndpoint
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.MarvelChars

class JikanAnimeLogic {

    suspend fun getAllAnimes():List<MarvelChars>{
        val call= ApiConnection.getJikanConnection()
        //Sobre la url base creo el endpoint
        val response=call.create(JikanEndpoint::class.java).getAllAnimes()
        var itemList= arrayListOf<MarvelChars>()
        if(response.isSuccessful){
            //La data es un listado que vamos a recorrer
            response.body()!!.data.forEach(){
                val m=MarvelChars(it.mal_id,
                    it.title,
                    it.duration,
                    it.synopsis,
                    it.images.jpg.image_url
                    )
                itemList.add(m)
            }
        }
        return itemList
    }

}