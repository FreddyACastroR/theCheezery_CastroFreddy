package mx.itson.thecheezery_252191.ui.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import mx.itson.thecheezery_252191.cheezeryApplication
import mx.itson.thecheezery_252191.data.database.entity.ProductEntity
import mx.itson.thecheezery_252191.data.database.relation.ComboWithProducts
import mx.itson.thecheezery_252191.data.repository.CheezeryRepository
import mx.itson.thecheezery_252191.domain.Category

class ShopViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: CheezeryRepository
) : ViewModel() {

    private val _cart = mutableStateListOf<ProductEntity>()
    val cart: List<ProductEntity> get() = _cart

    val total: Float by derivedStateOf { _cart.sumOf { it.price.toDouble() }.toFloat() }

    val combos: StateFlow<List<ComboWithProducts>> = repository.getCombosWithProducts()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        viewModelScope.launch {
            repository.insertDefaultProducts()

            // si android cerro la app se recupera la orden con los ids guardados
            val ids = savedStateHandle.get<List<Int>>("cart").orEmpty()
            for (id in ids) {
                val product = repository.getProductById(id)
                if (product != null && !isInCart(product)) {
                    _cart.add(product)
                }
            }
            guardarOrden()
        }
    }

    fun productsOf(category: Category): Flow<List<ProductEntity>> {
        return repository.getProductsByCategory(category.name)
    }

    fun isInCart(product: ProductEntity): Boolean {
        return _cart.any { it.id == product.id }
    }

    fun addToCart(product: ProductEntity) {
        if (!isInCart(product)) {
            _cart.add(product)
            guardarOrden()
        }
    }

    fun removeFromCart(product: ProductEntity) {
        _cart.removeAll { it.id == product.id }
        guardarOrden()
    }

    private fun guardarOrden() {
        savedStateHandle["cart"] = ArrayList(_cart.map { it.id })
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                ShopViewModel(createSavedStateHandle(), cheezeryApplication.repository)
            }
        }
    }
}
