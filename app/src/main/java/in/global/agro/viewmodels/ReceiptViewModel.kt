package `in`.global.agro.viewmodels

import `in`.global.agro.data.model.CustomerBalanceModel
import `in`.global.agro.data.model.ReceiptModel
import `in`.global.agro.data.repo.ReceiptRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(private val repository: ReceiptRepository) : ViewModel(){

    fun insert(receiptModel: ReceiptModel)=viewModelScope.launch {
        repository.insert(receiptModel)
    }

    fun getAllReceipt()=repository.getAllReceipt()

    fun addCustomerBalance(customerBalance: CustomerBalanceModel)=viewModelScope.launch {
        repository.addBalance(customerBalance)
    }
}