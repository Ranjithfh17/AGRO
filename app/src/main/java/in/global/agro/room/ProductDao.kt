package `in`.global.agro.room

import `in`.global.agro.data.model.ProductModel
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductDao {

    @Insert
    suspend fun addProduct(productModel: ProductModel)

    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<ProductModel>>

    @Delete
    suspend fun deleteProduct(productModel: ProductModel)

    @Query("SELECT * FROM products WHERE category=:category")
    fun getProductCategory(category: String):Flow<List<ProductModel>>



}