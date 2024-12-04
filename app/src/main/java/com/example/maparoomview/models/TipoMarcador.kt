package com.example.maparoomview.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "marcadores")
data class TipoMarcador(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tipoId: Long,
    val titulo: String
)
