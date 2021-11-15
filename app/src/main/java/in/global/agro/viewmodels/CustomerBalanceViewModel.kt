package `in`.global.agro.viewmodels

import `in`.global.agro.data.repo.CustomerBalanceRepository
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject



@HiltViewModel
class CustomerBalanceViewModel @Inject constructor(private val repository: CustomerBalanceRepository) :
    ViewModel() {

//        fun getAllBalanceList()=repository.getAllBalanceList()

}