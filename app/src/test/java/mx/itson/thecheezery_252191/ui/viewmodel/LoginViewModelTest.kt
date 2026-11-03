package mx.itson.thecheezery_252191.ui.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class LoginViewModelTest {

    private fun viewModel(email: String, password: String): LoginViewModel {
        val viewModel = LoginViewModel()
        viewModel.onEmailChange(email)
        viewModel.onPasswordChange(password)
        return viewModel
    }

    @Test
    fun login_withValidFields_succeeds() {
        val viewModel = viewModel("freddy.castro@itson.edu.mx", "queso")

        assertTrue(viewModel.login())
        assertNull(viewModel.state.emailError)
        assertNull(viewModel.state.passwordError)
    }

    @Test
    fun login_withEmptyFields_showsBothErrors() {
        val viewModel = viewModel("", "")

        assertFalse(viewModel.login())
        assertEquals(ERROR_CORREO_VACIO, viewModel.state.emailError)
        assertEquals(ERROR_CONTRASENA_VACIA, viewModel.state.passwordError)
    }

    @Test
    fun login_withOnlySpaces_countsAsEmpty() {
        val viewModel = viewModel("   ", "   ")

        assertFalse(viewModel.login())
        assertEquals(ERROR_CORREO_VACIO, viewModel.state.emailError)
        assertEquals(ERROR_CONTRASENA_VACIA, viewModel.state.passwordError)
    }

    @Test
    fun login_withBadEmail_showsEmailError() {
        val malos = listOf("freddy", "freddy@", "@itson.mx", "freddy@itson", "freddy@itson.m", "fre ddy@itson.mx")
        for (email in malos) {
            val viewModel = viewModel(email, "queso")

            assertFalse(email, viewModel.login())
            assertEquals(email, ERROR_CORREO_INVALIDO, viewModel.state.emailError)
            assertNull(email, viewModel.state.passwordError)
        }
    }

    @Test
    fun login_ignoresSpacesAroundEmail() {
        assertTrue(viewModel(" freddy@itson.mx ", "queso").login())
    }

    @Test
    fun typing_clearsTheErrorOfThatField() {
        val viewModel = viewModel("", "")
        viewModel.login()

        viewModel.onEmailChange("f")

        assertNull(viewModel.state.emailError)
        assertEquals(ERROR_CONTRASENA_VACIA, viewModel.state.passwordError)
    }
}
