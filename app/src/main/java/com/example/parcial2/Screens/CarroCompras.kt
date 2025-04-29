package com.example.parcial2.Screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parcial2.MainViewModel

@Composable
fun CarroCompras(viewModel: MainViewModel, onBack: () -> Unit) {
    Column(Modifier.padding(16.dp)) {
        Text("Carrito de Compras")

        viewModel.carrito.forEach {
            Text("${it.nombre} - \$${it.precio}")
        }

        Spacer(Modifier.height(8.dp))
        Text("Total: \$${viewModel.totalCarrito()}")

        Button(onClick = {
            viewModel.limpiarCarrito()
            // Aquí puedes usar Snackbar o un AlertDialog si quieres
        }) {
            Text("Finalizar Compra")
        }

        OutlinedButton(onClick = onBack) {
            Text("Volver al Catálogo")
        }
    }
}
