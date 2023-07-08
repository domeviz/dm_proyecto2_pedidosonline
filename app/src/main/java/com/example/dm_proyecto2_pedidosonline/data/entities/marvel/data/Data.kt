package com.example.dm_proyecto2_pedidosonline.data.entities.marvel.data

data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Result>,
    val total: Int
)