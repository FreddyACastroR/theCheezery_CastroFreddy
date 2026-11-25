package mx.itson.thecheezery_252191.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import mx.itson.thecheezery_252191.cheezeryApplication
import mx.itson.thecheezery_252191.data.database.entity.ProductEntity
import mx.itson.thecheezery_252191.data.repository.CheezeryRepository
import mx.itson.thecheezery_252191.domain.Category

const val ERROR_NOMBRE_VACIO = "Escribe el nombre"
const val ERROR_PRECIO_INVALIDO = "Escribe un precio mayor a 0"
const val ERROR_SIN_CATEGORIA = "Elige una categoría"

fun leerPrecio(texto: String): Float? {
    val precio = texto.trim().replace(',', '.').toFloatOrNull()
    if (precio == null || !precio.isFinite() || precio <= 0f) return null
    return precio
}

class AddProductViewModel(private val repository: CheezeryRepository) : ViewModel() {

    var state by mutableStateOf(AddProductState())
        private set

    var saved by mutableStateOf(false)
        private set

    private var saving = false

    fun onNameChange(name: String) {
        state = state.copy(name = name, nameError = null)
    }

    fun onPriceChange(price: String) {
        state = state.copy(price = price, priceError = null)
    }

    fun onDescriptionChange(description: String) {
        state = state.copy(description = description)
    }

    fun onCategoryChange(category: Category) {
        state = state.copy(category = category, categoryError = null)
    }

    fun saveProduct() {
        if (saving) return

        val name = state.name.trim()
        val price = leerPrecio(state.price)
        val category = state.category

        state = state.copy(
            nameError = if (name.isEmpty()) ERROR_NOMBRE_VACIO else null,
            priceError = if (price == null) ERROR_PRECIO_INVALIDO else null,
            categoryError = if (category == null) ERROR_SIN_CATEGORIA else null
        )
        if (name.isEmpty() || price == null || category == null) return

        val product = ProductEntity(
            name = name,
            price = price,
            description = state.description.trim().ifEmpty { null },
            category = category.name
        )
        saving = true
        viewModelScope.launch {
            repository.insertProduct(product)
            saved = true
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                AddProductViewModel(cheezeryApplication.repository)
            }
        }
    }
}
