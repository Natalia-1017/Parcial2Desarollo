package com.example.parcial2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.parcial2.Screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel: MainViewModel = viewModel()

            NavHost(navController = navController, startDestination = "catalogo") {
                composable("catalogo") {
                    CatalogoProductos(
                        viewModel,
                        onAgregar = { navController.navigate("agregar") },
                        onCarrito = { navController.navigate("carrito") },
                        onDetalle = { id -> navController.navigate("detalle/$id") }
                    )
                }
                composable("agregar") {
                    AgregarProducto(viewModel) {
                        navController.popBackStack()
                    }
                }
                composable("carrito") {
                    CarroCompras(viewModel) {
                        navController.popBackStack()
                    }
                }
                composable("detalle/{id}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                    if (id != null) {
                        DetalleProducto(viewModel, id) {
                            navController.popBackStack()
                        }
                    } else {
                        // Redirige a una pantalla de error si el ID es inválido
                        navController.navigate("error")
                    }
                }
                composable("error") {
                    ErrorScreen("Error: ID inválido")
                }
            }
        }
    }
}

@Composable
fun ErrorScreen(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message)
    }
}
