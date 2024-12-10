package com.example.maparoomview

import androidx.room.*

@Dao interface TipoLugarDao {
    @Insert
    suspend fun insertMarkerType(markerType: TipoLugar)

    @Query("SELECT * FROM tiposlugares")
    suspend fun getAllMarkerTypes(): List<TipoLugar>

    @Query("SELECT * FROM tiposlugares WHERE id = :id")
    fun getMarkTypeById(id: Int): TipoLugar?
}