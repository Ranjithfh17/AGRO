package `in`.global.agro.data.repo

import `in`.global.agro.room.CustomerBalanceDao
import `in`.global.agro.room.PurchaseDao
import `in`.global.agro.room.ReceiptDao
import `in`.global.agro.room.SalesDao
import javax.inject.Inject

class CustomerBalanceRepository @Inject constructor(
    private val customerBalanceDao: CustomerBalanceDao

) {

//    fun getAllBalanceList()=customerBalanceDao.getAllBalanceLIst()

}