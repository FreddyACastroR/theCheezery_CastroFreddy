package mx.itson.thecheezery_252191.ui.viewmodel

import mx.itson.thecheezery_252191.domain.Product

data class ProductItem(
    val product: Product,
    val inCart: Boolean
)
