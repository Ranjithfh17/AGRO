package `in`.global.agro.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class PurchaseInvoiceModel(
    val sno: Int,
    val name: String,
    val date: String,
    val product: String,
    val quantity: String,
    val price: String,
    val discount: String? = null,
    val tax: String? = null,
    val totalAmount: String,
):Parcelable