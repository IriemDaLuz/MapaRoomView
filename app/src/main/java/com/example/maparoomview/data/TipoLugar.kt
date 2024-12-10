package com.example.maparoomview

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tiposlugares")
data class TipoLugar(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)