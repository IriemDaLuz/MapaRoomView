package com.example.maparoomview

import androidx.room.*

@Dao interface LugarDao {
    @Insert
    suspend fun insert(marker: Lugar)

    @Delete
    suspend fun delete(marker: Lugar?)

    @Query("SELECT * FROM lugares")
    suspend fun getAllMarkers(): List<Lugar>

    @Query("SELECT * FROM lugares WHERE id = :id")
    fun getMarkerById(id: Int): Lugar?
}