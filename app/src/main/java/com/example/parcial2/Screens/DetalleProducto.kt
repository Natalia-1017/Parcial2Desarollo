package com.example.parcial2.Screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.parcial2.MainViewModel

@Composable
fun DetalleProducto(viewModel: MainViewModel, id: Int, onBack: () -> Unit) {
    val producto = viewModel.obtenerProductoPorId(id)

    if (producto == null) {
        Text("Producto no encontrado", Modifier.padding(16.dp))
        Button(onClick = onBack) { Text("Volver") }
        return
    }

    Column(Modifier.padding(16.dp)) {
        AsyncImage(
            model = producto.imagenUrl,
            contentDescription = producto.nombre,
            modifier = Modifier.height(200.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(producto.nombre)
        Text("Precio: \$${producto.precio}")
        Text(producto.descripcion)

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            viewModel.agregarAlCarrito(producto)
            onBack()
        }) {
            Text("Agregar al Carrito")
        }

        OutlinedButton(onClick = onBack) {
            Text("Volver")
        }
    }
}
