package com.example.dm_proyecto2_pedidosonline.ui.utilities

import android.app.Application
import androidx.room.Room
import com.example.dm_proyecto2_pedidosonline.data.connections.MarvelConnectionDB
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.data.database.MarvelCharsDB

class DM_Proyecto2_PedidosOnline: Application() {

    val name_class:String="Admin"
    override fun onCreate() {
        super.onCreate()
        //Se crea la aplicacion y se instancia con 1 sola variable
        db= Room.databaseBuilder(
            applicationContext,
            MarvelConnectionDB::class.java,
            "marvelDB"
        ).build()
    }

    //El companion object es un objeto que se crea dentro de una clase
    companion object{
        var db:MarvelConnectionDB?=null
        //Esto accede directamente a la DB
        fun getDbInstance():MarvelConnectionDB{
            return db!!
        }


    }
}