package mx.itson.thecheezery_252191.ui.viewmodel

data class LoginState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null
)
