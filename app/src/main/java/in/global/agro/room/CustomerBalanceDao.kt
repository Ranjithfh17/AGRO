package `in`.global.agro.room

import `in`.global.agro.data.model.CustomerBalanceModel
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface CustomerBalanceDao {

    @Insert
    suspend fun insert(customerBalanceModel: CustomerBalanceModel)

    @Query("SELECT * FROM customer_balance ")
    fun getAllBalanceLIst():Flow<List<CustomerBalanceModel>>

//    fun isCustomerExists(name:String) : Flow<List<CustomerBalanceModel>>

    @Update
    suspend fun update(customerBalanceModel: CustomerBalanceModel)

}