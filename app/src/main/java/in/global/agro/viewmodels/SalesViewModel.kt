package `in`.global.agro.viewmodels

import `in`.global.agro.data.model.CustomerBalanceModel
import `in`.global.agro.data.model.ProductItem
import `in`.global.agro.data.model.SalesModel
import `in`.global.agro.data.repo.SalesRepository
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SalesViewModel @Inject constructor(private val repository: SalesRepository) : ViewModel() {

    private val productList = arrayListOf<ProductItem>()


    private val _totalFlow = MutableStateFlow(0f)
    val totalFlow: StateFlow<Float> = _totalFlow


    private val _taxTypeFlow = MutableStateFlow("Exclude Tax")
    val taxTypeFlow: StateFlow<String> = _taxTypeFlow


    private val _productItemFlow = MutableStateFlow<List<ProductItem>>(listOf())
    val productItemFlow: StateFlow<List<ProductItem>> = _productItemFlow


    fun addSales(salesModel: SalesModel) = viewModelScope.launch {
        repository.addSales(salesModel)
    }

    fun getAllSales(): Flow<List<SalesModel>> = repository.getAllSales()


    fun getAllProducts() = repository.getAllProductS()



    fun addProductItem(productItem: ProductItem) = viewModelScope.launch {
        repository.addProductItem(productItem)
    }

    fun getProductItem() = repository.getProductItem()

    fun deleteProducts()=viewModelScope.launch {
        repository.deleteProducts()
    }

    fun getProductsForSpecificCustomer(id:Int)= repository.getProductsForSpecificCustomer(id)



    fun setTaxType(type: String) {
        _taxTypeFlow.value = type
    }


    fun calculateTotalAmount(
        quantity: Float,
        price: Float,
        taxType: String,
        tax: Float
    ) {
        when (taxType) {

            "Include Tax" -> {
                val subTotal = quantity * price
                val taxPercentage = subTotal * tax / 100f
                _totalFlow.value = taxPercentage + subTotal
                Log.i("TAG", "calculateTotalAmount  In:${_totalFlow.value} ")
            }

            "Exclude Tax" -> {
                val total = quantity * price
                _totalFlow.value = total
                Log.i("TAG", "calculateTotalAmount   ex:${_totalFlow.value} ")
            }


        }
    }


}