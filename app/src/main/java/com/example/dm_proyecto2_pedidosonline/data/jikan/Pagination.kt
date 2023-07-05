package com.example.dm_proyecto2_pedidosonline.data.jikan

data class Pagination(
    val current_page: Int,
    val has_next_page: Boolean,
    val items: Items,
    val last_visible_page: Int
)