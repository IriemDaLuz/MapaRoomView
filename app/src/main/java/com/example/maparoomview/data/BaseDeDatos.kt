package com.example.maparoomview

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Lugar::class, TipoLugar::class], version = 6)
abstract class BaseDeDatos : RoomDatabase() {

    abstract fun LugarDao(): LugarDao
    abstract fun TipoLugarDao(): TipoLugarDao


}