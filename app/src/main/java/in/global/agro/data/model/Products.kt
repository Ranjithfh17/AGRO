package `in`.global.agro.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Products(
    val product: String,
    val subtotal: String,
    val quantity: String,
    val price: String,
    val discount: String,
    val tax: String,
):Parcelable
