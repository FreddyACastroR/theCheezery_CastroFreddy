package mx.itson.thecheezery_252191.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import mx.itson.thecheezery_252191.data.database.entity.ComboEntity
import mx.itson.thecheezery_252191.data.database.relation.ComboWithProducts

@Dao
interface ComboDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCombo(combo: ComboEntity): Long

    @Update
    suspend fun updateCombo(combo: ComboEntity)

    @Delete
    suspend fun deleteCombo(combo: ComboEntity)

    @Query("SELECT * FROM Combos")
    fun getAllCombos(): Flow<List<ComboEntity>>

    // @Transaction porque room hace dos consultas: los combos y luego sus productos
    @Transaction
    @Query("SELECT * FROM Combos")
    fun getCombosWithProducts(): Flow<List<ComboWithProducts>>
}
