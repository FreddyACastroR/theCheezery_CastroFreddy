package mx.itson.thecheezery_252191.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mx.itson.thecheezery_252191.data.database.dao.ComboDao
import mx.itson.thecheezery_252191.data.database.dao.ProductComboDao
import mx.itson.thecheezery_252191.data.database.dao.ProductDao
import mx.itson.thecheezery_252191.data.database.entity.ComboEntity
import mx.itson.thecheezery_252191.data.database.entity.ProductComboEntity
import mx.itson.thecheezery_252191.data.database.entity.ProductEntity

@Database(
    entities = [
        ProductEntity::class,
        ComboEntity::class,
        ProductComboEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun comboDao(): ComboDao
    abstract fun productComboDao(): ProductComboDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // siempre regresa la misma base de datos, si no existe la crea
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cheezery.db"
                )
                    // en room 2.8 la version sin parametro esta deprecada
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
