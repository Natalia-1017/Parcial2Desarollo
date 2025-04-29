package com.example.parcial2


import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var productos by mutableStateOf(listOf<Producto>())
        private set

    var carrito by mutableStateOf(listOf<Producto>())
        private set

    fun agregarProducto(producto: Producto) {
        productos = productos + producto
    }

    fun agregarAlCarrito(producto: Producto) {
        carrito = carrito + producto
    }

    fun totalCarrito(): Double {
        return carrito.sumOf { it.precio }
    }

    fun limpiarCarrito() {
        carrito = emptyList()
    }

    fun obtenerProductoPorId(id: Int): Producto? {
        return productos.find { it.id == id }
    }
}
