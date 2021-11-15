package `in`.global.agro.room

import `in`.global.agro.data.model.PurchaseModel
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface PurchaseDao {


    @Insert
    suspend fun insert(purchaseModel: PurchaseModel)

    @Query("SELECT * FROM purchase")
    fun getPurchase():Flow<MutableList<PurchaseModel>>



}