package com.example.parcial2.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parcial2.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun CarroCompras(viewModel: MainViewModel, onBack: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center) // Centrado de toda la columna
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center // Centrado vertical de los elementos
            ) {
                // Imagen del carrito
                Image(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Carrito de Compras",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(bottom = 16.dp),
                )

                // Título
                Text(
                    text = "Carrito de Compras",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFA726),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if (viewModel.carrito.isEmpty()) {
                    Text("Tu carrito está vacío", fontSize = 18.sp, color = Color.Gray)
                } else {
                    viewModel.carrito.forEach {
                        Text(
                            text = "${it.nombre} = \$${it.precio}", // Cambiado el símbolo "-" por "="
                            fontSize = 18.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Total: \$${viewModel.totalCarrito()}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFFFA726),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón Finalizar Compra sin borde
                    Button(
                        onClick = {
                            viewModel.limpiarCarrito()
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("¡Compra realizada con éxito!")
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726))
                    ) {
                        Text("Finalizar Compra", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Botón Volver al Catálogo siempre visible
                Button(
                    onClick = onBack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726))
                ) {
                    Text("Volver al Catálogo", color = Color.White)
                }
            }
        }
    }
}
