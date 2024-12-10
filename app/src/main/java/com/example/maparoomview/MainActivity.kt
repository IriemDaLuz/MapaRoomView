package com.example.maparoomview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = BaseDeDatos.getDatabase(this)
        val LugarDao = database.LugarDao()
        val TipoLugarDao = database.TipoLugarDao()

        val ViewModelMapApp = ViewModelMap(LugarDao, TipoLugarDao)

        setContent {
            MainMapApp(ViewModelMapApp)
        }
    }
}