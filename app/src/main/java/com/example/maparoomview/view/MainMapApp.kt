package com.example.maparoomview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.utsman.osmandcompose.*
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.GeoPoint
import org.osmdroid.util.MapTileIndex

val GoogleSat: OnlineTileSourceBase = object : XYTileSource(
    "Google-Sat",
    0, 19, 256, ".png", arrayOf(
        "https://mt0.google.com",
        "https://mt1.google.com",
        "https://mt2.google.com",
        "https://mt3.google.com",
    )
) {
    override fun getTileURLString(pTileIndex: Long): String {
        return baseUrl + "/vt/lyrs=s&x=" + MapTileIndex.getX(pTileIndex) + "&y=" + MapTileIndex.getY(
            pTileIndex
        ) + "&z=" + MapTileIndex.getZoom(pTileIndex)
    }
}

@Composable
fun MainMapApp(ViewModelMap: ViewModelMap) {
    val lugares by ViewModelMap.lugares.collectAsState()
    val tiposlugares by ViewModelMap.tiposlugares.collectAsState()

    val cameraState = rememberCameraState {
        geoPoint = GeoPoint(41.38379275681205, 2.138805384392312)
        zoom = 16.5
    }

    var mapProperties by remember { mutableStateOf(DefaultMapProperties) }

    OpenStreetMap(
        modifier = Modifier.fillMaxSize(),
        cameraState = cameraState,
        properties = mapProperties
    ) {
        mapProperties = mapProperties
            .copy(tileSources = GoogleSat)
            .copy(isEnableRotationGesture = true)
            .copy(zoomButtonVisibility = ZoomButtonVisibility.SHOW_AND_FADEOUT)

        lugares.forEach { lugar ->
            val lugarState = rememberMarkerState(
                geoPoint = GeoPoint(
                    lugar.latitude.toDouble(),
                    lugar.longitude.toDouble()
                )
            )

            val nombreTipoLugar = tiposlugares[lugar.IdTipoLugar]

            val context = LocalContext.current

            val ImagenLugares = when (lugar.IdTipoLugar) {
                1-> R.drawable.museo
                2-> R.drawable.ocio
                3-> R.drawable.estadio
                4-> R.drawable.centro_comercial
                else -> R.drawable.otros
            }

            val drawable = ContextCompat.getDrawable(context, ImagenLugares)

            Marker(
                state = lugarState,
                title = lugar.name,
                snippet = nombreTipoLugar,
                icon = drawable
            ) {
                Column(
                    modifier = Modifier
                        .widthIn(0.dp, 250.dp)
                        .background(
                            color = Color(0xD0000000),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = it.title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )


                }
            }
        }
    }
}