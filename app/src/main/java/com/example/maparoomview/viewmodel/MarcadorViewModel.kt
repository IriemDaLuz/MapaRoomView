package com.example.maparoomview.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.maparoomview.database.MarcadorDatabase
import com.example.maparoomview.models.Marcador
import com.example.maparoomview.models.TipoMarcador
import kotlinx.coroutines.launch

class MarcadorViewModel(application: Application) : AndroidViewModel(application) {
    private val marcadorDao = MarcadorDatabase.getDatabase(application).marcadorDao()
    private val _marcadores = MutableLiveData<List<Marcador>>()
    val marcadores: LiveData<List<Marcador>> get() = _marcadores

    init {
        inicializarDatos()
    }

    fun obtenerMarcadores() {
        viewModelScope.launch {
            _marcadores.value = marcadorDao.obtenerTodosLosMarcadores()
        }
    }

    private fun inicializarDatos() {
        viewModelScope.launch {
            val tipos = listOf(
                TipoMarcador(nombre = "Alojamiento"),
                TipoMarcador(nombre = "Shopping"),
                TipoMarcador(nombre = "Parque"),
                TipoMarcador(nombre = "Playa")
            )
            tipos.forEach { marcadorDao.insertarTipoMarcador(it) }

            val marcadores = listOf(
                Marcador(tipoId = 1, titulo = "Alojamiento A", latitud = -12.0464, longitud = -77.0428),
                Marcador(tipoId = 2, titulo = "Shopping B", latitud = -12.0465, longitud = -77.0427),
                Marcador(tipoId = 3, titulo = "Parque C", latitud = -12.0466, longitud = -77.0426),
                Marcador(tipoId = 4, titulo = "Playa D", latitud = -12.0467, longitud = -77.0425)
            )
            marcadores.forEach { marcadorDao.insertarMarcador(it) }

            obtenerMarcadores()
        }
    }
}
