package mx.itson.thecheezery_252191.ui.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import mx.itson.thecheezery_252191.data.catalog
import mx.itson.thecheezery_252191.domain.Category
import mx.itson.thecheezery_252191.domain.Product

class ShopViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _cart = savedStateHandle.get<List<String>>("cart").orEmpty()
        .mapNotNull { name -> catalog.values.flatten().find { it.name == name } }
        .toMutableStateList()
    val cart: List<Product> get() = _cart

    val total: Float by derivedStateOf { _cart.sumOf { it.price.toDouble() }.toFloat() }

    fun productsOf(category: Category): List<ProductItem> {
        val products = catalog[category] ?: emptyList()
        return products.map { ProductItem(it, it in _cart) }
    }

    fun addToCart(product: Product) {
        if (!_cart.contains(product)) {
            _cart.add(product)
            guardarOrden()
        }
    }

    fun removeFromCart(product: Product) {
        _cart.remove(product)
        guardarOrden()
    }

    private fun guardarOrden() {
        savedStateHandle["cart"] = ArrayList(_cart.map { it.name })
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                ShopViewModel(createSavedStateHandle())
            }
        }
    }
}
