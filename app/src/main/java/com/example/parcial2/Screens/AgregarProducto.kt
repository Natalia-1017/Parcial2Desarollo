package com.example.parcial2.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.parcial2.MainViewModel
import com.example.parcial2.Producto
import com.example.parcial2.R

@Composable
fun AgregarProducto(viewModel: MainViewModel, onBack: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }
    var errorMensaje by remember { mutableStateOf<String?>(null) }

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Encabezado
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 12.dp)
        ) {
            Text(
                text = "Agregar Producto",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Imagen decorativa (como en catálogo)
        Image(
            painter = painterResource(id = R.drawable.elegir),
            contentDescription = "Encabezado",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Crop
        )

        // Contenido desplazable
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Campo Nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre", color = if (nombre.isNotEmpty()) Color(0xFFFFA726) else Color.Black) }, // Naranja cuando tiene valor, negro cuando no
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFFFFA726), // Naranja
                    unfocusedIndicatorColor = Color.Black, // Negro cuando no enfocado
                    focusedLabelColor = Color(0xFFFFA726), // Naranja
                    unfocusedLabelColor = if (nombre.isNotEmpty()) Color(0xFFFFA726) else Color.Black // Cambiar etiqueta entre negro y naranja
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Campo Precio
            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio", color = if (precio.isNotEmpty()) Color(0xFFFFA726) else Color.Black) }, // Naranja cuando tiene valor, negro cuando no
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFFFFA726), // Naranja
                    unfocusedIndicatorColor = Color.Black, // Negro cuando no enfocado
                    focusedLabelColor = Color(0xFFFFA726), // Naranja
                    unfocusedLabelColor = if (precio.isNotEmpty()) Color(0xFFFFA726) else Color.Black // Cambiar etiqueta entre negro y naranja
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Campo Descripción
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción", color = if (descripcion.isNotEmpty()) Color(0xFFFFA726) else Color.Black) }, // Naranja cuando tiene valor, negro cuando no
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFFFFA726), // Naranja
                    unfocusedIndicatorColor = Color.Black, // Negro cuando no enfocado
                    focusedLabelColor = Color(0xFFFFA726), // Naranja
                    unfocusedLabelColor = if (descripcion.isNotEmpty()) Color(0xFFFFA726) else Color.Black // Cambiar etiqueta entre negro y naranja
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Campo URL Imagen
            OutlinedTextField(
                value = imagenUrl,
                onValueChange = { imagenUrl = it },
                label = { Text("URL Imagen", color = if (imagenUrl.isNotEmpty()) Color(0xFFFFA726) else Color.Black) }, // Naranja cuando tiene valor, negro cuando no
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFFFFA726), // Naranja
                    unfocusedIndicatorColor = Color.Black, // Negro cuando no enfocado
                    focusedLabelColor = Color(0xFFFFA726), // Naranja
                    unfocusedLabelColor = if (imagenUrl.isNotEmpty()) Color(0xFFFFA726) else Color.Black // Cambiar etiqueta entre negro y naranja
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Imagen de vista previa si URL no está vacía
            if (imagenUrl.isNotBlank()) {
                val painter = rememberAsyncImagePainter(imagenUrl)
                val state = painter.state

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    when (state) {
                        is AsyncImagePainter.State.Loading -> {
                            CircularProgressIndicator()
                        }
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
                                    color = Color.Gray
                                )
                            }
                        }
                        else -> {
                            Image(
                                painter = painter,
                                contentDescription = "Vista previa",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }

            errorMensaje?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            // Botones (Guardar y Cancelar)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        val precioDouble = precio.replace(",", "").toDoubleOrNull()

                        if (nombre.isBlank() || descripcion.isBlank() || imagenUrl.isBlank() || precioDouble == null) {
                            errorMensaje = "Por favor llena todos los campos correctamente con datos válidos."
                        } else {
                            val producto = Producto(
                                id = System.currentTimeMillis().toInt(),
                                nombre = nombre,
                                precio = precioDouble,
                                descripcion = descripcion,
                                imagenUrl = imagenUrl
                            )
                            viewModel.agregarProducto(producto)
                            errorMensaje = null
                            onBack()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726))
                ) {
                    Text("Guardar", color = Color.White)
                }

                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726))
                ) {
                    Text("Cancelar", color = Color.White)
                }
            }
        }
    }
}
