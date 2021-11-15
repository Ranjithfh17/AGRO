package `in`.global.agro.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer_balance")
data class CustomerBalanceModel(
    val name:String,
    val balance:String?=null,
    val phoneNo:String?=null,
    val address:String?=null,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0
)
