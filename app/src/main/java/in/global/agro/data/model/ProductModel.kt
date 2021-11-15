package `in`.global.agro.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductModel(
    val productName: String,
    val price: String,
    val category:String?=null,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
