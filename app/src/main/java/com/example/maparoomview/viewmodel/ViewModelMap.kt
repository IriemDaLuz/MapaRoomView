package com.example.maparoomview

// Importación de clases y librerías necesarias para ViewModel, corrutinas y manejo de flujo de datos
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ViewModel para gestionar la información de lugares y tipos de lugares
class ViewModelMap(
    private val lugarDao: LugarDao, // DAO para acceder a la base de datos de lugares
    private val tipoLugarDao: TipoLugarDao // DAO para acceder a la base de datos de tipos de lugares
) : ViewModel() {

    // Estado que guarda la lista de lugares. Usamos un MutableStateFlow para poder modificarlo.
    private val _lugares = MutableStateFlow<List<Lugar>>(emptyList())
    val lugares: StateFlow<List<Lugar>> = _lugares // Exponemos el estado como un StateFlow para que sea inmutable desde fuera

    // Estado que guarda los tipos de lugares. Usamos un MutableStateFlow para poder modificarlo.
    private val _tiposlugares = MutableStateFlow<Map<Int, String>>(emptyMap())
    val tiposlugares: StateFlow<Map<Int, String>> = _tiposlugares // Exponemos el estado como un StateFlow para que sea inmutable desde fuera

    // Inicialización: se cargan los datos de lugares y tipos de lugares cuando se crea el ViewModel
    init {
        cargarMarcadores() // Cargar lugares
        cargarTiposMarcadores() // Cargar tipos de lugares
    }

    // Función para cargar los lugares desde la base de datos
    private fun cargarMarcadores() {
        // Lanzamos una corrutina en el contexto IO (para operaciones de entrada/salida)
        viewModelScope.launch(Dispatchers.IO) {
            // Obtenemos la lista de lugares desde el DAO
            val lugarList = lugarDao.getAllMarkers()
            // Actualizamos el estado con la lista de lugares obtenida
            _lugares.value = lugarList
        }
    }

    // Función para cargar los tipos de lugares desde la base de datos
    private fun cargarTiposMarcadores() {
        // Lanzamos una corrutina en el contexto IO (para operaciones de entrada/salida)
        viewModelScope.launch(Dispatchers.IO) {
            // Obtenemos los tipos de lugares desde el DAO
            val types = tipoLugarDao.getAllMarkerTypes()
            // Convertimos la lista de tipos a un mapa (ID -> nombre) y actualizamos el estado
            _tiposlugares.value = types.associate { it.id to it.name }
        }
    }

    // Función para insertar lugares de ejemplo en la base de datos
    private fun insertarLugares() {
        // Lista de lugares con los detalles de cada lugar
        val lugares = listOf(
            Lugar(name = "Sagrada Familia", latitude = "41.4036299", longitude = "2.1743558", IdTipoLugar = 1),
            Lugar(name = "Parque Güell", latitude = "41.414495", longitude = "2.152694", IdTipoLugar = 1),
            Lugar(name = "Camp Nou", latitude = "41.380896", longitude = "2.122820", IdTipoLugar = 3),
            Lugar(name = "Casa Batlló", latitude = "41.391682", longitude = "2.164921", IdTipoLugar = 1),
            Lugar(name = "Tibidabo", latitude = "41.422600", longitude = "2.117046", IdTipoLugar = 2),
            Lugar(name = "Montjuïc", latitude = "41.363633", longitude = "2.158003", IdTipoLugar = 2),
            Lugar(name = "Diagonal Mar", latitude = "41.408510", longitude = "2.216056", IdTipoLugar = 4),
            Lugar(name = "La Maquinista", latitude = "41.437520", longitude = "2.192768", IdTipoLugar = 4),
            Lugar(name = "PortAventura", latitude = "41.087560", longitude = "1.146771", IdTipoLugar = 2),
            Lugar(name = "Circuito de Cataluña", latitude = "41.570178", longitude = "2.261460", IdTipoLugar = 3),
            Lugar(name = "Museo Nacional de Arte de Cataluña (MNAC)", latitude = "41.368857", longitude = "2.154311", IdTipoLugar = 1),
            Lugar(name = "El Corte Inglés - Plaça Catalunya", latitude = "41.387017", longitude = "2.170112", IdTipoLugar = 4),
            Lugar(name = "Palau Sant Jordi", latitude = "41.364803", longitude = "2.152777", IdTipoLugar = 3),
            Lugar(name = "Aquarium de Barcelona", latitude = "41.376383", longitude = "2.186771", IdTipoLugar = 2),
            Lugar(name = "Plaza del Rey", latitude = "41.383036", longitude = "2.177293", IdTipoLugar = 5)
        )
        // Lanzamos una corrutina en el contexto IO para insertar los lugares en la base de datos
        CoroutineScope(Dispatchers.IO).launch {
            lugares.forEach { lugar ->
                lugarDao.insert(lugar) // Insertamos cada lugar en la base de datos
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

        // Lanzamos una corrutina en el contexto IO para insertar los tipos en la base de datos
        CoroutineScope(Dispatchers.IO).launch {
            tiposLugares.forEach { tipoLugar ->
                tipoLugarDao.insertMarkerType(tipoLugar) // Insertamos cada tipo de lugar en la base de datos
            }
        }
    }
}
