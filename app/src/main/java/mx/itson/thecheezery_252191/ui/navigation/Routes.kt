package mx.itson.thecheezery_252191.ui.navigation

import kotlinx.serialization.Serializable
import mx.itson.thecheezery_252191.domain.Category

@Serializable
object Welcome

@Serializable
object Login

@Serializable
object Menu

@Serializable
data class Products(val category: Category)

@Serializable
object Cart
