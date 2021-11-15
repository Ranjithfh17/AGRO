package `in`.global.agro.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "sales")
data class SalesModel(
    val name: String,
    val date: String,
    val products: List<Products>,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
) : Parcelable


