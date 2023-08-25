package com.example.dm_proyecto2_pedidosonline.data.entities.marvel

import android.os.Parcelable
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.data.database.MarvelCharsDB
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarvelChars(
    val id: Int,
    val nombre: String,
    val comic: String,
//    val descripcion: String,
    val imagen: String,
    var user:String
) : Parcelable
fun MarvelChars.getMarvelCharsDB(): MarvelCharsDB {
    return MarvelCharsDB(
        id,
        nombre,
        comic,
        imagen
    )
}
