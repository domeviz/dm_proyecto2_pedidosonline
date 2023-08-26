package com.example.dm_proyecto2_pedidosonline.data.connections

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dm_proyecto2_pedidosonline.data.dao.marvel.MarvelCharsDao
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.data.database.MarvelCharsDB

@Database(entities = [MarvelCharsDB::class], version = 1)
abstract class MarvelConnectionDB:RoomDatabase() {
    abstract fun marvelDao():MarvelCharsDao
}