package mx.itson.thecheezery_252191.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.itson.thecheezery_252191.data.database.entity.ProductComboEntity

@Dao
interface ProductComboDao {

    // si el producto ya estaba en ese combo se ignora (el indice es unique)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProductCombo(productCombo: ProductComboEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProductsCombo(productsCombo: List<ProductComboEntity>)

    @Delete
    suspend fun deleteProductCombo(productCombo: ProductComboEntity)

    @Query("SELECT * FROM ProductsCombo WHERE idCombo = :comboId")
    suspend fun getProductsCombo(comboId: Int): List<ProductComboEntity>
}
