package com.example.maparoomview.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.maparoomview.models.Marcador
import com.example.maparoomview.models.TipoMarcador


@Dao
interface MarcadorDao {
    @Insert
    suspend fun insertarMarcador(marcador: Marcador)

    @Insert
    suspend fun insertarTipoMarcador(tipo: TipoMarcador)

    @Query("SELECT * FROM marcadores")
    suspend fun obtenerTodosLosMarcadores(): List<Marcador>
}
