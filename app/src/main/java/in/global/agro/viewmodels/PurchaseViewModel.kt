package `in`.global.agro.viewmodels

import `in`.global.agro.data.model.CustomerBalanceModel
import `in`.global.agro.data.model.PurchaseModel
import `in`.global.agro.data.repo.PurchaseRepository
import `in`.global.agro.ui.fragments.CustomerBalance
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PurchaseViewModel @Inject constructor(private val repository: PurchaseRepository) :
    ViewModel() {

    private val _taxTypeFlow = MutableStateFlow("Exclude Tax")
    val taxTypeFlow: StateFlow<String> = _taxTypeFlow


    fun addPurchase(purchaseModel: PurchaseModel) = viewModelScope.launch {
        repository.addPurchase(purchaseModel)
    }

    fun getAllPurchase() = repository.getPurchase()

    fun getAllProducts()=repository.getAllProducts()


    fun setTaxType(type: String) {
        _taxTypeFlow.value = type
    }


    fun addCustomerBalance(customerBalance: CustomerBalanceModel)=viewModelScope.launch {
        repository.addCustomerBalance(customerBalance)
    }

//    fun isCustomerExists(name:String)=repository.isCustomerExists(name)


    fun update(customerBalance: CustomerBalanceModel)=viewModelScope.launch {
        repository.update(customerBalance)
    }


}