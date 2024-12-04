package com.example.maparoomview.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.maparoomview.data.MarcadorDao
import com.example.maparoomview.models.Marcador
import com.example.maparoomview.models.TipoMarcador

@Database(entities = [Marcador::class, TipoMarcador::class], version = 1)
abstract class MarcadorDatabase : RoomDatabase() {
    abstract fun marcadorDao(): MarcadorDao

    companion object {
        @Volatile
        private var INSTANCE: MarcadorDatabase? = null


    }
}
