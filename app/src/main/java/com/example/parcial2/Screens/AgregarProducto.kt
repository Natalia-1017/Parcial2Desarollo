package com.example.parcial2.Screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.parcial2.MainViewModel
import com.example.parcial2.Producto

@Composable
fun AgregarProducto(viewModel: MainViewModel, onBack: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }
    var errorMensaje by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // Input para el nombre del producto
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") }
        )

        // Input para el precio del producto
        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)  // Configuración para el teclado numérico
        )

        // Input para la descripción del producto
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") }
        )

        // Input para la URL de la imagen del producto
        OutlinedTextField(
            value = imagenUrl,
            onValueChange = { imagenUrl = it },
            label = { Text("URL Imagen") }
        )

        // Mostrar la imagen si la URL no está vacía
        if (imagenUrl.isNotBlank()) {
            val painter = rememberImagePainter(imagenUrl)
            Image(
                painter = painter,
                contentDescription = "Imagen del producto",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        // Mostrar mensaje de error si existe
        errorMensaje?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para guardar el producto
        Button(onClick = {
            try {
                // Limpiar el campo de precio de comas y otros caracteres no numéricos
                val precioDouble = precio.replace(",", "").toDoubleOrNull()

                // Validación de entrada
                if (nombre.isBlank() || descripcion.isBlank() || imagenUrl.isBlank() || precioDouble == null) {
                    errorMensaje = "Por favor llena todos los campos correctamente con datos válidos."
                } else {
                    // Crear un ID único basado en el tiempo (puedes cambiar esto a un mecanismo más adecuado)
                    val producto = Producto(
                        id = System.currentTimeMillis().toInt(),  // Usamos el tiempo como ID único
                        nombre = nombre,
                        precio = precioDouble,
                        descripcion = descripcion,
                        imagenUrl = imagenUrl
                    )
                    // Agregar el producto al ViewModel
                    viewModel.agregarProducto(producto)
                    errorMensaje = null
                    onBack()  // Regresar a la pantalla anterior
                }
            } catch (e: Exception) {
                // Captura cualquier error y muestra un mensaje en lugar de cerrar la app
                errorMensaje = "Ocurrió un error al guardar el producto: ${e.message}"
            }
        }) {
            Text("Guardar Producto")
        }

        // Botón para cancelar la acción y regresar
        OutlinedButton(onClick = onBack) {
            Text("Cancelar")
        }
    }
}
