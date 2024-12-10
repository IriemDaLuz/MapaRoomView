package com.example.maparoomview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.collections.forEach

class ViewModelMap(
    private val lugarDao: LugarDao,
    private val tipoLugarDao:  TipoLugarDao
) : ViewModel() {
    private val _lugares = MutableStateFlow<List<Lugar>>(emptyList())
    val lugares: StateFlow<List<Lugar>> = _lugares

   private val _tiposlugares = MutableStateFlow<Map<Int, String>>(emptyMap())
    val tiposlugares: StateFlow<Map<Int, String>> = _tiposlugares


    init {
        cargarMarcadores()
        cargarTiposMarcadores()
    }

    private fun cargarMarcadores() {
        viewModelScope.launch(Dispatchers.IO) {
            val lugarList = lugarDao.getAllMarkers()
            _lugares.value = lugarList
        }
    }

    private fun cargarTiposMarcadores() {
        viewModelScope.launch(Dispatchers.IO) {
            val types = tipoLugarDao.getAllMarkerTypes()
            _tiposlugares.value = types.associate { it.id to it.name }
        }
    }

    private fun insertarLugares() {
        // Lista de lugares a añadir
        val lugares = listOf(
            Lugar(
                name = "Sagrada Familia",
                latitude = "41.4036299",
                longitude = "2.1743558",
                IdTipoLugar = 1
            ),
            Lugar(
                name = "Parque Güell",
                latitude = "41.414495",
                longitude = "2.152694",
                IdTipoLugar = 1
            ),
            Lugar(
                name = "Camp Nou",
                latitude = "41.380896",
                longitude = "2.122820",
                IdTipoLugar = 3,
            ),
            Lugar(
                name = "Casa Batlló",
                latitude = "41.391682",
                longitude = "2.164921",
                IdTipoLugar = 1,
            ),
            Lugar(
                name = "Tibidabo",
                latitude = "41.422600",
                longitude = "2.117046",
                IdTipoLugar = 2,
            ),
            Lugar(
                name = "Montjuïc",
                latitude = "41.363633",
                longitude = "2.158003",
                IdTipoLugar = 2,
            ),
            Lugar(
                name = "Diagonal Mar",
                latitude = "41.408510",
                longitude = "2.216056",
                IdTipoLugar = 4,
            ),
            Lugar(
                name = "La Maquinista",
                latitude = "41.437520",
                longitude = "2.192768",
                IdTipoLugar = 4,
            ),
            Lugar(
                name = "PortAventura",
                latitude = "41.087560",
                longitude = "1.146771",
                IdTipoLugar = 2,
            ),
            Lugar(
                name = "Circuito de Cataluña",
                latitude = "41.570178",
                longitude = "2.261460",
                IdTipoLugar = 3,
            ),
            Lugar(
                name = "Museo Nacional de Arte de Cataluña (MNAC)",
                latitude = "41.368857",
                longitude = "2.154311",
                IdTipoLugar = 1,
            ),
            Lugar(
                name = "El Corte Inglés - Plaça Catalunya",
                latitude = "41.387017",
                longitude = "2.170112",
                IdTipoLugar = 4,
            ),
            Lugar(
                name = "Palau Sant Jordi",
                latitude = "41.364803",
                longitude = "2.152777",
                IdTipoLugar = 3,
            ),
            Lugar(
                name = "Aquarium de Barcelona",
                latitude = "41.376383",
                longitude = "2.186771",
                IdTipoLugar = 2,
            ),
            Lugar(
                name = "Plaza del Rey",
                latitude = "41.383036",
                longitude = "2.177293",
                IdTipoLugar = 5,
            )
        )
        CoroutineScope(Dispatchers.IO).launch {
            lugares.forEach { lugar ->
                lugarDao.insert(lugar)
            }
        }
    }

    private fun insertarTiposLugares() {
        val tiposLugares = listOf(
            TipoLugar(name = "Monumentos"),
            TipoLugar(name = "Actividades"),
            TipoLugar(name = "Deportivos"),
            TipoLugar(name = "Centros Comerciales"),
            TipoLugar(name = "Otros")
        )

        CoroutineScope(Dispatchers.IO).launch {
            tiposLugares.forEach { tipoLugar ->
                tipoLugarDao.insertMarkerType(tipoLugar)
            }
        }
    }


}