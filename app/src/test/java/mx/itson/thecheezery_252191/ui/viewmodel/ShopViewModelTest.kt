package mx.itson.thecheezery_252191.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import mx.itson.thecheezery_252191.data.coldDrinks
import mx.itson.thecheezery_252191.data.hotDrinks
import mx.itson.thecheezery_252191.data.salties
import mx.itson.thecheezery_252191.data.sweets
import mx.itson.thecheezery_252191.domain.Category
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ShopViewModelTest {

    private val panini = salties[2]
    private val coldBrew = coldDrinks[2]

    @Test
    fun productsOf_givesEachCategoryItsOwnList() {
        val viewModel = ShopViewModel(SavedStateHandle())

        assertEquals(hotDrinks, viewModel.productsOf(Category.HOT_DRINKS).map { it.product })
        assertEquals(coldDrinks, viewModel.productsOf(Category.COLD_DRINKS).map { it.product })
        assertEquals(salties, viewModel.productsOf(Category.SALTIES).map { it.product })
        assertEquals(sweets, viewModel.productsOf(Category.SWEETS).map { it.product })
    }

    @Test
    fun cart_startsEmptyWithZeroTotal() {
        val viewModel = ShopViewModel(SavedStateHandle())

        assertTrue(viewModel.cart.isEmpty())
        assertEquals(0f, viewModel.total, 0f)
        assertTrue(viewModel.productsOf(Category.SALTIES).none { it.inCart })
    }

    @Test
    fun addToCart_addsTheProductAndMarksItInItsList() {
        val viewModel = ShopViewModel(SavedStateHandle())

        viewModel.addToCart(panini)

        assertEquals(listOf(panini), viewModel.cart)
        val salties = viewModel.productsOf(Category.SALTIES)
        assertTrue(salties.single { it.product == panini }.inCart)
        assertEquals(1, salties.count { it.inCart })
    }

    @Test
    fun addToCart_sameProductTwice_keepsOne() {
        val viewModel = ShopViewModel(SavedStateHandle())

        viewModel.addToCart(panini)
        viewModel.addToCart(panini)

        assertEquals(listOf(panini), viewModel.cart)
    }

    @Test
    fun total_isTheSumOfTheCart() {
        val viewModel = ShopViewModel(SavedStateHandle())
        // la orden del pdf: 4 + 3 + 4 + 7 + 6
        listOf(panini, coldBrew, coldDrinks[3], salties[4], sweets[5]).forEach(viewModel::addToCart)

        assertEquals(24f, viewModel.total, 0f)
    }

    @Test
    fun removeFromCart_updatesCartTotalAndList() {
        val viewModel = ShopViewModel(SavedStateHandle())
        viewModel.addToCart(panini)
        viewModel.addToCart(coldBrew)

        viewModel.removeFromCart(panini)

        assertEquals(listOf(coldBrew), viewModel.cart)
        assertEquals(coldBrew.price, viewModel.total, 0f)
        assertFalse(viewModel.productsOf(Category.SALTIES).single { it.product == panini }.inCart)
    }

    @Test
    fun cart_isRestoredWithTheSavedState() {
        // como si android cerrara la app y creara otro viewmodel con lo guardado
        val savedState = SavedStateHandle()
        val before = ShopViewModel(savedState)
        before.addToCart(panini)
        before.addToCart(coldBrew)
        before.removeFromCart(panini)

        val after = ShopViewModel(savedState)

        assertEquals(listOf(coldBrew), after.cart)
        assertEquals(coldBrew.price, after.total, 0f)
        assertTrue(after.productsOf(Category.COLD_DRINKS).single { it.product == coldBrew }.inCart)
    }
}
