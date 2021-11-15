package `in`.global.agro.data.repo

import `in`.global.agro.data.model.ProductModel
import `in`.global.agro.prefernces.MainPreference
import `in`.global.agro.room.ProductDao
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productDao: ProductDao,private val mainPreference: MainPreference) {

    suspend fun addProduct(productModel: ProductModel)=productDao.addProduct(productModel)

    fun getAllProduct()=productDao.getAllProducts()

    suspend fun deleteProduct(productModel: ProductModel)=productDao.deleteProduct(productModel)

    fun getProductCategory(category: String)=productDao.getProductCategory(category)

    suspend fun saveProductFilterType(value:String)=mainPreference.saveProductFilterTYpe(value)

    fun getProductFilterType()=mainPreference.getProductFilterType()


}