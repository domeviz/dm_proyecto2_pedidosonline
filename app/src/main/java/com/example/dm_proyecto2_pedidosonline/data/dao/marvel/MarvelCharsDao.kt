package com.example.dm_proyecto2_pedidosonline.data.dao.marvel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.data.database.MarvelCharsDB

@Dao
interface MarvelCharsDao {
    @Query("select * from MarvelCharsDB")
    fun getAllCharacters() : List<MarvelCharsDao>

    @Query("select * from MarvelCharsDB where id=:idd")
    fun getOneCharacters(idd: Int) : List<MarvelCharsDB>

    @Insert
    fun insertMarvelChar(ch : List<MarvelCharsDao>)
}