package `in`.global.agro.data.repo

import `in`.global.agro.room.PurchaseDao
import `in`.global.agro.room.SalesDao
import javax.inject.Inject

class StatementRepository @Inject constructor(
    private val salesDao: SalesDao,
    private val purchaseDao: PurchaseDao
) {

    fun getCustomerDetails(name:String)=salesDao.getCustomerDetails(name)


}