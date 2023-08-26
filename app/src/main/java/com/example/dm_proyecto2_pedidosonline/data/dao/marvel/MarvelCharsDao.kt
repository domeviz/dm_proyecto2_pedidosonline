package com.example.dm_proyecto2_pedidosonline.data.dao.marvel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.data.database.MarvelCharsDB

@Dao
interface MarvelCharsDao {
    @Query("select * from MarvelCharsDB")
    fun getAllCharacters() : List<MarvelCharsDB>

    @Query("select * from MarvelCharsDB where id=:id")
    fun getOneCharacters(id: Int) : MarvelCharsDB

    @Insert
    fun insertMarvelChar(ch : List<MarvelCharsDB>)
}