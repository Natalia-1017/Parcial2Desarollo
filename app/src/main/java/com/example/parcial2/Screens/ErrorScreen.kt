package com.example.parcial2.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun ErrorScreen(mensaje: String, onBack: () -> Unit) {
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
                // Imagen de error
                Image(
                    imageVector = Icons.Default.Error,
                    contentDescription = "Error",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(bottom = 16.dp),
                )

                // Título de error
                Text(
                    text = "¡Algo salió mal!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFA726),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Mensaje de error
                Text(
                    text = mensaje,
                    fontSize = 18.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Botón Volver al Catálogo
                Button(
                    onClick = onBack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726))
                ) {
                    Text("Volver al Catálogo", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Snackbar para notificar al usuario sobre el error
                LaunchedEffect(Unit) {
                    snackbarHostState.showSnackbar("Por favor, intenta nuevamente.")
                }
            }
        }
    }
}
