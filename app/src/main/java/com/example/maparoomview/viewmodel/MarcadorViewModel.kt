package com.example.maparoomview.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.maparoomview.database.MarcadorDatabase
import com.example.maparoomview.models.Marcador
import kotlinx.coroutines.launch

class MarcadorViewModel(application: Application) : AndroidViewModel(application) {
    private val marcadorDao = MarcadorDatabase.getDatabase(application).marcadorDao()
    private val _marcadores = MutableLiveData<List<Marcador>>()
    val marcadores: LiveData<List<Marcador>> get() = _marcadores

    fun obtenerMarcadores() {
        viewModelScope.launch {
            _marcadores.value = marcadorDao.obtenerTodosLosMarcadores()
        }
    }
}
