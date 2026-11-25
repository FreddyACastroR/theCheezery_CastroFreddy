package mx.itson.thecheezery_252191.ui.viewmodel

import mx.itson.thecheezery_252191.domain.Category

data class AddProductState(
    val name: String = "",
    val price: String = "",
    val description: String = "",
    val category: Category? = null,
    val nameError: String? = null,
    val priceError: String? = null,
    val categoryError: String? = null
)
