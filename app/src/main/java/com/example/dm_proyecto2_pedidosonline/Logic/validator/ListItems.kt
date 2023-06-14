package com.example.dm_proyecto2_pedidosonline.Logic.validator

import com.example.dm_proyecto2_pedidosonline.data.entities.MarvelHero

class ListItems {
    fun returnMarvelChars(): List<MarvelHero> {
        val items = listOf(
            MarvelHero(
                1,
                "Spider-Man 2099",
                "Exiles",
                "https://comicvine.gamespot.com/a/uploads/scale_small/11170/111705043/8700768-spider-man2099.jpg"
            ),
            MarvelHero(
                2,
                "Black Cat",
                "The Amazing Spider-Man",
                "https://comicvine.gamespot.com/a/uploads/scale_small/11144/111442876/8759849-grr.jpg"
            ),
            MarvelHero(
                3,
                "Blade",
                "Avengers",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/7949396-heroes_reborn_vol_2_1_devil_dog_comics_exclusive_virgin_variant.jpg"
            ),
            MarvelHero(
                4,
                "Havok",
                "The Uncanny X-Men",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8717952-ezgif-1-1197bb8aab.jpg"
            ),
            MarvelHero(
                5,
                "Howard the Duck",
                "Howard the Duck",
                "https://comicvine.gamespot.com/a/uploads/scale_small/3/31666/4282626-howard2015001-cov-7364c.jpg"
            )
        )
        return items
    }
}