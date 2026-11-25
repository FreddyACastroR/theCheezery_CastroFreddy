package mx.itson.thecheezery_252191.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idProduct")
    val id: Int = 0,

    @ColumnInfo(name = "nameProduct")
    val name: String,

    @ColumnInfo(name = "priceProduct")
    val price: Float,

    // se guarda el nombre del drawable, los productos nuevos no tienen imagen
    @ColumnInfo(name = "imageProduct")
    val image: String? = null,

    @ColumnInfo(name = "descriptionProduct")
    val description: String? = null,

    // nombre de la categoria: HOT_DRINKS, COLD_DRINKS, SALTIES o SWEETS
    @ColumnInfo(name = "categoryProduct")
    val category: String
)
