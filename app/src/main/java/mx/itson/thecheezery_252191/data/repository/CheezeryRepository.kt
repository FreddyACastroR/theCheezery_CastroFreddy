package mx.itson.thecheezery_252191.data.repository

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import mx.itson.thecheezery_252191.data.database.AppDatabase
import mx.itson.thecheezery_252191.data.database.entity.ComboEntity
import mx.itson.thecheezery_252191.data.database.entity.ProductComboEntity
import mx.itson.thecheezery_252191.data.database.entity.ProductEntity
import mx.itson.thecheezery_252191.data.database.relation.ComboWithProducts
import mx.itson.thecheezery_252191.data.defaultProducts

class CheezeryRepository(private val database: AppDatabase) {

    private val productDao = database.productDao()
    private val comboDao = database.comboDao()
    private val productComboDao = database.productComboDao()

    // productos

    // la primera vez la tabla esta vacia y se llena con el menu de la cafeteria
    suspend fun insertDefaultProducts() {
        database.withTransaction {
            if (productDao.countProducts() == 0) {
                productDao.insertProducts(defaultProducts)
            }
        }
    }

    suspend fun insertProduct(product: ProductEntity): Long {
        return productDao.insertProduct(product)
    }

    fun getAllProducts(): Flow<List<ProductEntity>> {
        return productDao.getAllProducts()
    }

    suspend fun getProductById(id: Int): ProductEntity? {
        return productDao.getProductById(productId = id)
    }

    fun getProductsByCategory(category: String): Flow<List<ProductEntity>> {
        return productDao.getProductsByCategory(category)
    }

    // combos

    // se guarda el combo y despues sus productos, todo junto en una transaccion
    suspend fun insertComboWithProducts(combo: ComboEntity, productIds: List<Int>) {
        database.withTransaction {
            val idCombo = comboDao.insertCombo(combo).toInt()
            val productsCombo = productIds.map { ProductComboEntity(idProduct = it, idCombo = idCombo) }
            productComboDao.insertProductsCombo(productsCombo)
        }
    }

    fun getCombosWithProducts(): Flow<List<ComboWithProducts>> {
        return comboDao.getCombosWithProducts()
    }
}
