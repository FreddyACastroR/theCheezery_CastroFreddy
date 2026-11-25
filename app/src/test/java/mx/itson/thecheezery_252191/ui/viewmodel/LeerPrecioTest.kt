package mx.itson.thecheezery_252191.ui.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class LeerPrecioTest {

    @Test
    fun leerPrecio_conPuntoOComa_daElMismoNumero() {
        assertEquals(4.5f, leerPrecio("4.5"))
        assertEquals(4.5f, leerPrecio("4,5"))
        assertEquals(10f, leerPrecio(" 10 "))
    }

    @Test
    fun leerPrecio_invalido_daNull() {
        val malos = listOf("", "   ", "abc", "0", "-3", "NaN", "Infinity", "4.5.1")
        for (texto in malos) {
            assertNull(texto, leerPrecio(texto))
        }
    }
}
