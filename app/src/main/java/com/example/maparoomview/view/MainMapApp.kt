package com.example.maparoomview

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
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
                        .background(
                            color = Color(0xFF3F51B5),
                            shape = RoundedCornerShape(0.dp)
                        )
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = it.title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = it.snippet,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    val ImagenDescLugares = when (lugar.imageResourceId) {
                        1-> R.drawable.sagradafamilia
                        2-> R.drawable.parqueguell
                        3-> R.drawable.campnou
                        4-> R.drawable.casabatllo
                        5-> R.drawable.tibidabo
                        6-> R.drawable.montjuic
                        7-> R.drawable.diagonalmar
                        8-> R.drawable.lamaquinista
                        9-> R.drawable.portaventura
                        10-> R.drawable.circuitocat
                        11-> R.drawable.mnac
                        12-> R.drawable.corteingles
                        13-> R.drawable.palausantjordi
                        14-> R.drawable.aquarium
                        15-> R.drawable.plazadelrey
                        else -> R.drawable.otros
                    }

                    Image(
                        painter = painterResource(id = ImagenDescLugares),
                        contentDescription = lugar.name,
                        modifier = Modifier
                            .padding(8.dp)
                            .widthIn(max = 200.dp)
                    )

                    Text(
                        text = lugar.description,
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                }
            }
        }
    }
}