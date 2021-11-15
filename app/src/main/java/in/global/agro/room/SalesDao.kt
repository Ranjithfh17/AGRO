package `in`.global.agro.room

import `in`.global.agro.data.model.ProductItem
import `in`.global.agro.data.model.SalesModel
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface SalesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(salesModel: SalesModel)


    @Query("SELECT * FROM sales")
    fun getSales(): Flow<List<SalesModel>>


    @Query("SELECT * FROM sales WHERE name=:name")
    fun getCustomerDetails(name: String): Flow<List<SalesModel>>


    @Insert
    suspend fun addProductItem(productItem: ProductItem)


    @Query("SELECT * FROM product_items")
    fun getProductItem(): Flow<List<ProductItem>>


    @Query("DELETE FROM product_items")
    suspend fun deleteProducts()


    @Query("SELECT * FROM sales WHERE id=:id")
    fun getProductsForSpecificCustomer(id: Int): Flow<List<SalesModel>>



}