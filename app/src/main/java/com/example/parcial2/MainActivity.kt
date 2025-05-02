package com.example.parcial2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
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
                        // Asegúrate de que se hace popBackStack correctamente después de agregar un producto
                        navController.popBackStack()
                    }
                }

                composable("carrito") {
                    CarroCompras(viewModel) {
                        // PopBackStack después de realizar alguna acción en el carrito
                        navController.popBackStack()
                    }
                }

                composable("detalle/{id}") { backStackEntry ->
                    // Asegúrate de que el ID es válido y maneja el caso cuando no lo sea
                    val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                    if (id != null) {
                        val producto = viewModel.obtenerProductoPorId(id)
                        if (producto != null) {
                            DetalleProducto(viewModel, id) {
                                // PopBackStack después de ver detalles del producto
                                navController.popBackStack()
                            }
                        } else {
                            // Si el producto no existe, navega a la pantalla de error
                            LaunchedEffect(Unit) {
                                navController.navigate("error/Producto no encontrado")
                            }
                        }
                    } else {
                        // Si el ID no es válido, navega a la pantalla de error
                        LaunchedEffect(Unit) {
                            navController.navigate("error/ID inválido")
                        }
                    }
                }

                composable("error/{mensaje}") { backStackEntry ->
                    val mensaje = backStackEntry.arguments?.getString("mensaje") ?: "Error desconocido"
                    ErrorScreen(mensaje) {
                        // Después de mostrar el error, regresa al catálogo
                        navController.navigate("catalogo") {
                            popUpTo("catalogo") { inclusive = true }
                        }
                    }
                }
            }
        }
    }
}

