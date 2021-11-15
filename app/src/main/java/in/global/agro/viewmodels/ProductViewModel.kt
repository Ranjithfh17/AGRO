package `in`.global.agro.viewmodels

import `in`.global.agro.data.model.ProductModel
import `in`.global.agro.data.repo.ProductRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductRepository) :ViewModel() {


    fun addProduct(productModel: ProductModel)=viewModelScope.launch {
        repository.addProduct(productModel)
    }


    fun getAllProducts()=repository.getAllProduct()


    fun deleteProduct(productModel: ProductModel)=viewModelScope.launch {
        repository.deleteProduct(productModel)
    }


    fun getProductCategory(category: String)=repository.getProductCategory(category)


    fun saveProductFilterType(value:String)=viewModelScope.launch {
        repository.saveProductFilterType(value)
    }

    fun getProductFilterType()=repository.getProductFilterType()


}