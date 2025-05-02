package com.example.parcial2.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import coil.compose.*
import com.example.parcial2.MainViewModel
import com.example.parcial2.R
import kotlinx.coroutines.delay

@Composable
fun CatalogoProductos(
    viewModel: MainViewModel,
    onAgregar: () -> Unit,
    onCarrito: () -> Unit,
    onDetalle: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 12.dp)
        ) {
            Text(
                text = "Catálogo",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.supermercado),
            contentDescription = "Catálogo",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = { onAgregar() },
                    modifier = Modifier
                        .size(60.dp)
                        .clip(MaterialTheme.shapes.large)
                        .background(Color(0xFFFFA726))
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.White)
                }
                Text("Agregar", fontSize = 12.sp)
            }

            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .offset(y = (-30).dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Total Carrito", fontSize = 12.sp)
                    Text(
                        text = "$${viewModel.totalCarrito()}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = onCarrito,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(MaterialTheme.shapes.large)
                        .background(Color(0xFFFFA726))
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito", tint = Color.White)
                }
                Text("Ver Carrito", fontSize = 12.sp)
                Text("(${viewModel.carrito.size})", fontSize = 12.sp)
            }
        }

        Text(
            text = "Productos",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(viewModel.productos) { producto ->
                var mostrarPlaceholder by remember(producto.id) {
                    mutableStateOf(viewModel.nuevosProductos.contains(producto.id))
                }

                // Mostrar placeholder solo por 2 segundos si es nuevo
                if (mostrarPlaceholder) {
                    LaunchedEffect(producto.id) {
                        delay(2000)
                        mostrarPlaceholder = false
                        viewModel.marcarProductoProcesado(producto.id)
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDetalle(producto.id) }
                ) {
                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        when {
                            mostrarPlaceholder -> {
                                Image(
                                    painter = painterResource(id = R.drawable.placeholder),
                                    contentDescription = "Cargando imagen",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            else -> {
                                val painter = rememberAsyncImagePainter(model = producto.imagenUrl)
                                val state = painter.state

                                when (state) {
                                    is AsyncImagePainter.State.Error -> {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center,
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.LightGray)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.BrokenImage,
                                                contentDescription = "Imagen no disponible",
                                                tint = Color.Gray,
                                                modifier = Modifier.size(64.dp)
                                            )
                                            Text(
                                                text = "Imagen no disponible",
                                                fontSize = 12.sp,
                                                color = Color.Gray,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                    else -> {
                                        Image(
                                            painter = painter,
                                            contentDescription = producto.nombre,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFFFA106))
                            .padding(8.dp)
                    ) {
                        Column {
                            Text(
                                text = producto.nombre,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "$${producto.precio}",
                                color = Color.Black,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
