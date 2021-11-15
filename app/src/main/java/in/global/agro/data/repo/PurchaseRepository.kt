package `in`.global.agro.data.repo

import `in`.global.agro.data.model.CustomerBalanceModel
import `in`.global.agro.data.model.PurchaseModel
import `in`.global.agro.room.SalesDao
import `in`.global.agro.data.model.SalesModel
import `in`.global.agro.room.CustomerBalanceDao
import `in`.global.agro.room.ProductDao
import `in`.global.agro.room.PurchaseDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PurchaseRepository @Inject constructor(
    private val purchaseDao: PurchaseDao,
    private val productDao: ProductDao,
    private val customerBalanceDao: CustomerBalanceDao
) {


    suspend fun addPurchase(purchaseModel: PurchaseModel) = purchaseDao.insert(purchaseModel)

    fun getPurchase() = purchaseDao.getPurchase()

    fun getAllProducts()=productDao.getAllProducts()

    suspend fun addCustomerBalance(customerBalanceModel: CustomerBalanceModel)=customerBalanceDao.insert(customerBalanceModel)

//    fun isCustomerExists(name:String)=customerBalanceDao.isCustomerExists(name)

    suspend fun update(customerBalanceModel: CustomerBalanceModel)=customerBalanceDao.update(customerBalanceModel)
}