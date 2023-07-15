package com.example.dm_proyecto2_pedidosonline.Logic.marvelLogic

import android.util.Log
import com.example.dm_proyecto2_pedidosonline.data.connections.ApiConnection
import com.example.dm_proyecto2_pedidosonline.data.endpoints.MarvelEndpoint
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.MarvelChars
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.data.database.MarvelCharsDB
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.data.database.getMarvelChars
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.data.getMarvelChars
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.getMarvelCharsDB
import com.example.dm_proyecto2_pedidosonline.ui.utilities.DM_Proyecto2_PedidosOnline

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
    suspend fun getAllCharactersDB():List<MarvelChars>{

        var itemList= arrayListOf<MarvelChars>()

        val itemsAux = DM_Proyecto2_PedidosOnline.getDbInstance().marvelDao().getAllCharacters()

        itemsAux.forEach {
            itemList.add(it.getMarvelChars()
            )
        }
        return itemList
    }
    suspend fun insertMarvelCharstoDB(items:List<MarvelChars>){
        var itemsDB = arrayListOf<MarvelCharsDB>()
        items.forEach {
            itemsDB.add(it.getMarvelCharsDB())
        }
        DM_Proyecto2_PedidosOnline.getDbInstance().marvelDao().insertMarvelChar(itemsDB)
    }
    suspend fun getInitChars(page:Int):MutableList<MarvelChars>{
        var items= mutableListOf<MarvelChars>()
        try{
            items=MarvelLogic().getAllCharactersDB().toMutableList()

            if (items.isEmpty()){
//                items=(MarvelLogic().getAllMarvelChars(0,page*3))
//                items=(MarvelLogic().getAllMarvelChars(0,page*3).toMutableList())
                items=(MarvelLogic().getAllMarvelChars(0,page*3) as MutableList<MarvelChars>)
                MarvelLogic(). insertMarvelCharstoDB(items)
            }
        }catch(ex:Exception){
            throw RuntimeException(ex.message)
        }
        return items
    }
    suspend fun getAllCharsDB(offset:Int,limit: Int):MutableList<MarvelChars>{
        var marvelCharsItems= mutableListOf<MarvelChars>()
        try {
            marvelCharsItems=
                getAllCharactersDB().toMutableList()

            if (marvelCharsItems.isEmpty()) {
                marvelCharsItems = (MarvelLogic().getAllMarvelChars(
                    offset,
                    limit
                ).toMutableList())
                Log.d("Prueba",marvelCharsItems.size.toString())
                insertMarvelCharstoDB(marvelCharsItems)

            }
            return marvelCharsItems
        }catch (ex:Exception){
            throw RuntimeException(ex.message)
        }

        Log.d("MC",marvelCharsItems.size.toString())

    }
}