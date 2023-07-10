package com.example.dm_proyecto2_pedidosonline.data.entities.marvel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarvelChars(
    val id: Int,
    val nombre: String,
    val comic: String,
//    val descripcion: String,
    val imagen: String
) :
    Parcelable