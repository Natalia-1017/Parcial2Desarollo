package com.example.parcial2.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.parcial2.MainViewModel

@Composable
fun DetalleProducto(viewModel: MainViewModel, id: Int, onBack: () -> Unit) {
    val producto = viewModel.obtenerProductoPorId(id)

    // Si no se encuentra el producto, muestra la pantalla de error reutilizada
    if (producto == null) {
        // Aquí llamamos a la pantalla de error
        ErrorScreen(mensaje = "Producto no encontrado", onBack = onBack)
        return
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Detalles del Producto",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        val painter = rememberAsyncImagePainter(model = producto.imagenUrl)
        val state = painter.state

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is AsyncImagePainter.State.Error -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = producto.nombre,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Precio: $${producto.precio}",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = producto.descripcion,
            fontSize = 16.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Justify
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.agregarAlCarrito(producto)
                onBack()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726))
        ) {
            Text("Agregar al Carrito", color = Color.White)
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726))
        ) {
            Text("Volver", color = Color.White)
        }
    }
}
