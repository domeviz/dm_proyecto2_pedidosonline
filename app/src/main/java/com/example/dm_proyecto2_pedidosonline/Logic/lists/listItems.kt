package com.example.dm_proyecto2_pedidosonline.Logic.lists

import com.example.dm_proyecto1_foodapp.data.entities.LoginUser
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.MarvelChars

class listItems {

    fun returnItems():List<LoginUser>{
        var items= listOf<LoginUser>(
            LoginUser("1","1"),
            LoginUser("2","1"),
            LoginUser("3","1"),
            LoginUser("4","1"),
            LoginUser("5","1")
        )
        return items
    }
}