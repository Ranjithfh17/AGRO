package `in`.global.agro.data.repo

import `in`.global.agro.api.AgroApi
import `in`.global.agro.data.model.CustomerBalanceModel
import `in`.global.agro.data.model.ProductItem
import `in`.global.agro.room.SalesDao
import `in`.global.agro.data.model.SalesModel
import `in`.global.agro.room.CustomerBalanceDao
import `in`.global.agro.room.ProductDao
import `in`.global.agro.ui.fragments.CustomerBalance
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Field
import javax.inject.Inject

class SalesRepository @Inject constructor(
    private val salesDao: SalesDao,
    private val productDao: ProductDao,
    private val customerBalanceDao: CustomerBalanceDao
) {

    suspend fun addSales(salesModel: SalesModel) = salesDao.insert(salesModel = salesModel)

    fun getAllSales(): Flow<List<SalesModel>> = salesDao.getSales()

    fun getAllProductS() = productDao.getAllProducts()


    suspend fun addProductItem(productItem: ProductItem) = salesDao.addProductItem(productItem)

    fun getProductItem() = salesDao.getProductItem()

    suspend fun deleteProducts() = salesDao.deleteProducts()

    fun getProductsForSpecificCustomer(id: Int) = salesDao.getProductsForSpecificCustomer(id)


}