package mx.itson.thecheezery_252191.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import mx.itson.thecheezery_252191.cheezeryApplication
import mx.itson.thecheezery_252191.data.database.entity.ComboEntity
import mx.itson.thecheezery_252191.data.database.entity.ProductEntity
import mx.itson.thecheezery_252191.data.repository.CheezeryRepository

const val ERROR_POCOS_PRODUCTOS = "Elige al menos 2 productos"

class AddComboViewModel(private val repository: CheezeryRepository) : ViewModel() {

    var state by mutableStateOf(AddComboState())
        private set

    var saved by mutableStateOf(false)
        private set

    private var saving = false

    val products: Flow<List<ProductEntity>> = repository.getAllProducts()

    fun onNameChange(name: String) {
        state = state.copy(name = name, nameError = null)
    }

    fun onPriceChange(price: String) {
        state = state.copy(price = price, priceError = null)
    }

    fun onProductClick(product: ProductEntity) {
        val selected = if (product.id in state.selected) {
            state.selected - product.id
        } else {
            state.selected + product.id
        }
        state = state.copy(selected = selected, productsError = null)
    }

    fun saveCombo() {
        if (saving) return

        val name = state.name.trim()
        val price = leerPrecio(state.price)
        val pocosProductos = state.selected.size < 2

        state = state.copy(
            nameError = if (name.isEmpty()) ERROR_NOMBRE_VACIO else null,
            priceError = if (price == null) ERROR_PRECIO_INVALIDO else null,
            productsError = if (pocosProductos) ERROR_POCOS_PRODUCTOS else null
        )
        if (name.isEmpty() || price == null || pocosProductos) return

        val productIds = state.selected
        saving = true
        viewModelScope.launch {
            repository.insertComboWithProducts(ComboEntity(name = name, price = price), productIds)
            saved = true
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                AddComboViewModel(cheezeryApplication.repository)
            }
        }
    }
}
