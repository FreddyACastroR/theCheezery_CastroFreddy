package mx.itson.thecheezery_252191.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

const val ERROR_CORREO_VACIO = "Escribe tu correo electrónico"
const val ERROR_CORREO_INVALIDO = "El correo no tiene un formato válido"
const val ERROR_CONTRASENA_VACIA = "Escribe tu contraseña"

class LoginViewModel : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$")

    fun onEmailChange(email: String) {
        state = state.copy(email = email, emailError = null)
    }

    fun onPasswordChange(password: String) {
        state = state.copy(password = password, passwordError = null)
    }

    fun login(): Boolean {
        val email = state.email.trim()
        var emailError: String? = null
        var passwordError: String? = null

        if (email.isEmpty()) {
            emailError = ERROR_CORREO_VACIO
        } else if (!emailRegex.matches(email)) {
            emailError = ERROR_CORREO_INVALIDO
        }

        if (state.password.isBlank()) {
            passwordError = ERROR_CONTRASENA_VACIA
        }

        state = state.copy(emailError = emailError, passwordError = passwordError)
        return emailError == null && passwordError == null
    }
}
