package com.example.maparoomview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModelMap(
    private val lugarDao: LugarDao,
    private val tipoLugarDao: TipoLugarDao
) : ViewModel() {

        // Estado que guarda la lista de lugares. Usamos un MutableStateFlow para poder modificarlo.
        private val _lugares = MutableStateFlow<List<Lugar>>(emptyList())
        val lugares: StateFlow<List<Lugar>> = _lugares

        // Estado que guarda los tipos de lugares. Usamos un MutableStateFlow para poder modificarlo.
        private val _tiposlugares = MutableStateFlow<Map<Int, String>>(emptyMap())
        val tiposlugares: StateFlow<Map<Int, String>> = _tiposlugares

        // Inicialización: se cargan los datos de lugares y tipos de lugares cuando se crea el ViewModel
        init {
            cargarMarcadores() // Cargar lugares
            cargarTiposMarcadores() // Cargar tipos de lugares
        }

        // Función para cargar los lugares desde la base de datos
        private fun cargarMarcadores() {
            viewModelScope.launch(Dispatchers.IO) {
                val lugarList = lugarDao.getAllMarkers()
                _lugares.value = lugarList
            }
        }

        // Función para cargar los tipos de lugares desde la base de datos
        private fun cargarTiposMarcadores() {
            viewModelScope.launch(Dispatchers.IO) {
                val types = tipoLugarDao.getAllMarkerTypes()
                _tiposlugares.value = types.associate { it.id to it.name }
            }
        }

        // Función para insertar lugares de ejemplo en la base de datos
        private fun insertarLugares() {
            // Lista de lugares con los detalles de cada lugar
            val lugares = listOf(
                Lugar(
                    name = "Sagrada Familia",
                    latitude = "41.4036299",
                    longitude = "2.1743558",
                    IdTipoLugar = 1,
                    image = "sagradafamilia.jpeg",
                    description = "Una basílica icónica diseñada por Gaudí."
                ),
                Lugar(
                    name = "Parque Güell",
                    latitude = "41.414495",
                    longitude = "2.152694",
                    IdTipoLugar = 1,
                    image = "parqueguell.jpeg",
                    description = "Un parque colorido lleno de mosaicos y arquitectura única."
                ),
                Lugar(
                    name = "Camp Nou",
                    latitude = "41.380896",
                    longitude = "2.122820",
                    IdTipoLugar = 3,
                    image = "campnou.jpeg",
                    description = "El estadio del FC Barcelona, un icono deportivo."
                ),
                Lugar(
                    name = "Casa Batlló",
                    latitude = "41.391682",
                    longitude = "2.164921",
                    IdTipoLugar = 1,
                    image = "casabatllo.jpeg",
                    description = "Una obra maestra modernista de Antoni Gaudí."
                ),
                Lugar(
                    name = "Tibidabo",
                    latitude = "41.422600",
                    longitude = "2.117046",
                    IdTipoLugar = 2,
                    image = "tibidabo.jpeg",
                    description = "Un parque de atracciones con vistas espectaculares de Barcelona."
                ),
                Lugar(
                    name = "Montjuïc",
                    latitude = "41.363633",
                    longitude = "2.158003",
                    IdTipoLugar = 2,
                    image = "montjuic.jpeg",
                    description = "Una montaña llena de historia, cultura y eventos deportivos."
                ),
                Lugar(
                    name = "Diagonal Mar",
                    latitude = "41.408510",
                    longitude = "2.216056",
                    IdTipoLugar = 4,
                    image = "diagonalmar.jpeg",
                    description = "Un gran centro comercial cerca del mar."
                ),
                Lugar(
                    name = "La Maquinista",
                    latitude = "41.437520",
                    longitude = "2.192768",
                    IdTipoLugar = 4,
                    image = "lamaquinista.jpeg",
                    description = "Un centro comercial con una amplia variedad de tiendas."
                ),
                Lugar(
                    name = "PortAventura",
                    latitude = "41.087560",
                    longitude = "1.146771",
                    IdTipoLugar = 2,
                    image = "portaventura.jpeg",
                    description = "Un parque temático emocionante cerca de Barcelona."
                ),
                Lugar(
                    name = "Circuito de Cataluña",
                    latitude = "41.570178",
                    longitude = "2.261460",
                    IdTipoLugar = 3,
                    image = "circuitocat.jpeg",
                    description = "Un circuito de carreras reconocido a nivel mundial."
                ),
                Lugar(
                    name = "Museo Nacional de Arte de Cataluña (MNAC)",
                    latitude = "41.368857",
                    longitude = "2.154311",
                    IdTipoLugar = 1,
                    image = "mnac.jpeg",
                    description = "Un museo que alberga arte catalán de diversas épocas."
                ),
                Lugar(
                    name = "El Corte Inglés - Plaça Catalunya",
                    latitude = "41.387017",
                    longitude = "2.170112",
                    IdTipoLugar = 4,
                    image = "corteingles.jpeg",
                    description = "Un centro comercial emblemático en el corazón de la ciudad."
                ),
                Lugar(
                    name = "Palau Sant Jordi",
                    latitude = "41.364803",
                    longitude = "2.152777",
                    IdTipoLugar = 3,
                    image = "palausantjordi.jpeg",
                    description = "Un pabellón multifuncional para eventos deportivos y conciertos."
                ),
                Lugar(
                    name = "Aquarium de Barcelona",
                    latitude = "41.376383",
                    longitude = "2.186771",
                    IdTipoLugar = 2,
                    image = "aquarium.jpeg",
                    description = "Un acuario con una impresionante colección de vida marina."
                ),
                Lugar(
                    name = "Plaza del Rey",
                    latitude = "41.383036",
                    longitude = "2.177293",
                    IdTipoLugar = 5,
                    image = "plazadelrey.jpeg",
                    description = "Una plaza histórica en el corazón del Barrio Gótico."
                )
            )

            CoroutineScope(Dispatchers.IO).launch {
                lugares.forEach { lugar ->
                    lugarDao.insert(lugar)
                }
            }
        }

        // Función para insertar tipos de lugares de ejemplo en la base de datos
        private fun insertarTiposLugares() {
            // Lista de tipos de lugares con sus nombres
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
