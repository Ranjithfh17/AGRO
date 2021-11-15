package `in`.global.agro.data.repo

import `in`.global.agro.data.model.CustomerBalanceModel
import `in`.global.agro.data.model.ReceiptModel
import `in`.global.agro.room.CustomerBalanceDao
import `in`.global.agro.room.ReceiptDao
import javax.inject.Inject


class ReceiptRepository @Inject constructor(
    private val receiptDao: ReceiptDao,
    private val customerBalanceDao: CustomerBalanceDao
) {

    suspend fun insert(receiptModel: ReceiptModel) = receiptDao.insert(receiptModel = receiptModel)

    fun getAllReceipt() = receiptDao.getAllReceipt()

    suspend fun addBalance(customerBalance: CustomerBalanceModel) =
        customerBalanceDao.insert(customerBalance)


}