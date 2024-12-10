package com.example.maparoomview

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Lugar::class, TipoLugar::class], version = 6)
abstract class BaseDeDatos : RoomDatabase() {

    abstract fun LugarDao(): LugarDao
    abstract fun TipoLugarDao(): TipoLugarDao


    companion object {
        @Volatile
        private var INSTANCE: BaseDeDatos? = null

        fun getDatabase(context: Context): BaseDeDatos {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BaseDeDatos::class.java,
                    "basededatos_mapa"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}