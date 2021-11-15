package `in`.global.agro.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "product_items")
data class ProductItem(
    val product: String,
    val subtotal: String,
    val quantity: String,
    val price: String,
    val discount: String,
    val tax: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) : Parcelable
