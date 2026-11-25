package mx.itson.thecheezery_252191.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import mx.itson.thecheezery_252191.data.database.AppDatabase
import mx.itson.thecheezery_252191.data.database.entity.ComboEntity
import mx.itson.thecheezery_252191.data.database.entity.ProductEntity
import mx.itson.thecheezery_252191.data.repository.CheezeryRepository
import mx.itson.thecheezery_252191.domain.Category
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CheezeryRepositoryTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private lateinit var database: AppDatabase

    private lateinit var repository: CheezeryRepository

    @Before
    fun setUp() {
        // bd en memoria para no tocar la de la app
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        repository = CheezeryRepository(database)
    }

    @After
    fun tearDown() {
        database.close()
    }

    // busca un producto ya guardado (con su id de la bd) por su nombre
    private suspend fun saved(name: String): ProductEntity {
        return repository.getAllProducts().first().first { it.name == name }
    }

    @Test
    fun insertDefaultProducts_onlyFillsTheTableOnce() = runBlocking {
        repository.insertDefaultProducts()
        repository.insertDefaultProducts()

        val products = repository.getAllProducts().first()
        assertEquals(23, products.size)
        assertEquals(defaultProducts.map { it.name }, products.map { it.name })
    }

    @Test
    fun getProductsByCategory_onlyGivesThatCategory() = runBlocking {
        repository.insertDefaultProducts()

        val products = repository.getProductsByCategory(Category.SALTIES.name).first()

        assertEquals(salties.map { it.name }, products.map { it.name })
    }

    @Test
    fun insertProduct_canBeFoundById() = runBlocking {
        val id = repository.insertProduct(
            ProductEntity(name = "Bagel", price = 3.5f, category = Category.SALTIES.name)
        ).toInt()

        val product = repository.getProductById(id)

        assertEquals("Bagel", product?.name)
        assertEquals(3.5f, product?.price)
        assertNull(product?.image)
        assertNull(repository.getProductById(id + 1))
    }

    @Test
    fun insertComboWithProducts_comesBackWithItsProducts() = runBlocking {
        repository.insertDefaultProducts()
        val latte = saved("Latte")
        val tiramisu = saved("Tiramisu")

        repository.insertComboWithProducts(
            ComboEntity(name = "Combo dulce", price = 10f),
            listOf(latte.id, tiramisu.id)
        )

        val combos = repository.getCombosWithProducts().first()
        assertEquals(1, combos.size)
        assertEquals("Combo dulce", combos[0].combo.name)
        assertEquals(10f, combos[0].combo.price)
        assertEquals(setOf(latte, tiramisu), combos[0].products.toSet())
    }

    @Test
    fun deletingAProduct_removesItFromTheCombos() = runBlocking {
        repository.insertDefaultProducts()
        val latte = saved("Latte")
        val tiramisu = saved("Tiramisu")
        repository.insertComboWithProducts(
            ComboEntity(name = "Combo dulce", price = 10f),
            listOf(latte.id, tiramisu.id)
        )

        // por el CASCADE de ProductsCombo
        database.productDao().deleteProduct(latte)

        val combo = repository.getCombosWithProducts().first().single()
        assertEquals(listOf(tiramisu), combo.products)
        assertTrue(database.productComboDao().getProductsCombo(combo.combo.id).none { it.idProduct == latte.id })
    }
}
