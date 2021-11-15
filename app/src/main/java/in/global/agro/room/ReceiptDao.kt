package `in`.global.agro.room

import `in`.global.agro.data.model.ReceiptModel
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ReceiptDao {

    @Insert
    suspend fun insert(receiptModel: ReceiptModel)

    @Query("SELECT * FROM receipt")
    fun getAllReceipt(): Flow<List<ReceiptModel>>
}