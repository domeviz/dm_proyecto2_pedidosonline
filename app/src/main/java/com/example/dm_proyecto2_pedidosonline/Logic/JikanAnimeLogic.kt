package com.example.dm_proyecto2_pedidosonline.Logic

import com.example.dm_proyecto2_pedidosonline.data.connections.ApiConnection
import com.example.dm_proyecto2_pedidosonline.data.endpoints.JikanEndpoint
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.characters.MarvelChars

class JikanAnimeLogic {
    suspend fun getAllAnimes(): List<MarvelChars> {
        var itemList = arrayListOf<MarvelChars>()
        val call = ApiConnection.getService(
            ApiConnection.TypeApi.Jikan,
            JikanEndpoint::class.java)
        //Sobre la url base creo el endpoint
        val response = call.getAllAnimes()

        if (response.isSuccessful) {
            //La data es un listado que vamos a recorrer
            response.body()!!.data.forEach() {
                val m = MarvelChars(
                    it.mal_id, it.title, it.titles[0].title, it.images.jpg.image_url
                )
                itemList.add(m)
            }
        }
        return itemList
    }
}