package mx.itson.thecheezery_252191.data.database.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import mx.itson.thecheezery_252191.data.database.entity.ComboEntity
import mx.itson.thecheezery_252191.data.database.entity.ProductComboEntity
import mx.itson.thecheezery_252191.data.database.entity.ProductEntity

// el JOIN entre Combos y Products usando la tabla ProductsCombo
data class ComboWithProducts(
    @Embedded
    val combo: ComboEntity,

    @Relation(
        parentColumn = "idCombo",
        entityColumn = "idProduct",
        associateBy = Junction(
            value = ProductComboEntity::class,
            parentColumn = "idCombo",
            entityColumn = "idProduct"
        )
    )
    val products: List<ProductEntity>
)
