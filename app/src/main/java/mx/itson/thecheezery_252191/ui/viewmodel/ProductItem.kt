package mx.itson.thecheezery_252191.ui.viewmodel

import mx.itson.thecheezery_252191.data.database.entity.ProductEntity

data class ProductItem(
    val product: ProductEntity,
    val inCart: Boolean
)
