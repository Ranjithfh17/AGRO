package `in`.global.agro.viewmodels

import `in`.global.agro.data.model.PayList
import `in`.global.agro.data.model.PayTypeItem
import `in`.global.agro.data.repo.ProductWiseRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductWiseViewModel @Inject constructor(private val repository: ProductWiseRepository) :
    ViewModel() {

    private val _payFlow =
        MutableStateFlow<`in`.global.agro.utils.Resource<List<PayTypeItem>>>(`in`.global.agro.utils.Resource.Loading())
    val payFlow: StateFlow<`in`.global.agro.utils.Resource<List<PayTypeItem>>>
        get() = _payFlow



    init {
        getPayList()
    }



    private fun getPayList() = viewModelScope.launch {

        try {
            val response=repository.getPayList("1","21472147")
            _payFlow.value=`in`.global.agro.utils.Resource.Success(response)

        } catch (exception: Exception) {
            _payFlow.value=`in`.global.agro.utils.Resource.Error(exception.message.toString())

        }



    }
}