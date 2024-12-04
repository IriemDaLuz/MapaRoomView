package com.example.maparoomview.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tipos_marcadores")
data class TipoMarcador(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String
)