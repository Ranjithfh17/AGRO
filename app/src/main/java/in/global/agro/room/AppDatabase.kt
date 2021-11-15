package `in`.global.agro.room

import `in`.global.agro.data.model.*
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters


@Database(
    entities = [SalesModel::class, PurchaseModel::class, ReceiptModel::class, ProductModel::class, CustomerBalanceModel::class, ProductItem::class],
    version = 1
)

@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {

    abstract fun salesDao(): SalesDao

    abstract fun purchaseDao(): PurchaseDao

    abstract fun receiptDao(): ReceiptDao

    abstract fun productSDao(): ProductDao

    abstract fun customerBalanceDao(): CustomerBalanceDao

}