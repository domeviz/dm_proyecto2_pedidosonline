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
    fun returnMarvelChars():List<MarvelChars>{
        val items= listOf(
            MarvelChars(
                1,
                "Spiderman",
                "Spiderman (1962)",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8126579-amazing_spider-man_vol_5_54_stormbreakers_variant_textless.jpg"
            ),
            MarvelChars(
                2,
                "Invisible Woman",
                "Fantastic Four 1 (1961)",
                "https://comicvine.gamespot.com/a/uploads/scale_small/11141/111413247/7267710-4df69cb3-7b54-480e-89e5-2c7e68c52b1b_rw_1200.jpg"
            ),
            MarvelChars(
                3,
                "Eternity",
                "Strange Tales #138",
                "https://comicvine.gamespot.com/a/uploads/scale_small/10/105264/6154236-eternity%20universe.jpg"
            ),
            MarvelChars(
                4,
                "Black Widow",
                "The Avengers (1963)",
                "https://comicvine.gamespot.com/a/uploads/scale_small/1/14487/7835285-b7f54158-13c3-4646-bbee-033a765c570d.jpeg"
            ),
            MarvelChars(
                5,
                "Scarlet Witch",
                "The Avengers (1963)",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8926321-large-3540780.jpg"
            )

        )
        return items
    }
}