package com.example.parcial2.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.parcial2.MainViewModel

@Composable
fun CatalogoProductos(
    viewModel: MainViewModel,
    onAgregar: () -> Unit,
    onCarrito: () -> Unit,
    onDetalle: (Int) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onAgregar) {
                Icon(Icons.Default.AddShoppingCart, contentDescription = "Agregar")
                Spacer(Modifier.width(8.dp))
                Text("Agregar Producto")
            }
            Button(onClick = onCarrito) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                Spacer(Modifier.width(8.dp))
                Text("Ver Carrito")
            }
        }

        Text(
            text = "Total carrito: \$${viewModel.totalCarrito()}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            itemsIndexed(viewModel.productos) { _, producto ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDetalle(producto.id) },
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = producto.imagenUrl,
                            contentDescription = producto.nombre,
                            modifier = Modifier
                                .size(72.dp)
                                .clip(MaterialTheme.shapes.small)
                        )
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text(
                                text = producto.nombre,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Precio: \$${producto.precio}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
