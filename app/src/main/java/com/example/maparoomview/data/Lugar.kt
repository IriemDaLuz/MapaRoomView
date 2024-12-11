package com.example.maparoomview

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "lugares",
    foreignKeys = [
        ForeignKey(
            entity = TipoLugar::class,
            parentColumns = ["id"],
            childColumns = ["IdTipoLugar"],
        )
    ]
)

data class Lugar(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val latitude: String,
    val longitude: String,
    val IdTipoLugar: Int,
    val image: String,
    val description: String

)
