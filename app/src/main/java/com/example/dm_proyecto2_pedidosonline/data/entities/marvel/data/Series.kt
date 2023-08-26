package com.example.dm_proyecto2_pedidosonline.data.entities.marvel.data

data class Series(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)