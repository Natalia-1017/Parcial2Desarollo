package com.example.parcial2

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val productos = mutableStateListOf<Producto>()
    val carrito = mutableStateListOf<Producto>()

    private val _nuevosProductos = mutableStateListOf<Int>() // IDs de productos nuevos
    val nuevosProductos: List<Int> get() = _nuevosProductos

    fun agregarProducto(producto: Producto) {
        productos.add(producto)
        _nuevosProductos.add(producto.id)
    }

    fun marcarProductoProcesado(id: Int) {
        _nuevosProductos.remove(id)
    }

    fun agregarAlCarrito(producto: Producto) {
        carrito.add(producto)
    }

    fun eliminarDelCarrito(producto: Producto) {
        carrito.remove(producto)
    }

    fun limpiarCarrito() {
        carrito.clear()
    }

    fun obtenerProductoPorId(id: Int): Producto? {
        return productos.find { it.id == id }
    }

    fun totalCarrito(): Double {
        return carrito.sumOf { it.precio }
    }

    fun cantidadProductosCarrito(): Int {
        return carrito.size
    }
}
