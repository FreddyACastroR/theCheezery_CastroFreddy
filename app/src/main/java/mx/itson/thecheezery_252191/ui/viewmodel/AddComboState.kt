package mx.itson.thecheezery_252191.ui.viewmodel

data class AddComboState(
    val name: String = "",
    val price: String = "",
    val selected: List<Int> = emptyList(),
    val nameError: String? = null,
    val priceError: String? = null,
    val productsError: String? = null
)
