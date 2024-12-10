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





}