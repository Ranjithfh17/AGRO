package `in`.global.agro.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "receipt")
data class ReceiptModel(
    val date:String,
    val name:String,
    val payType:String,
    val payAccount:String,
    val amount:String,
    val balance:String?=null,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0

)
