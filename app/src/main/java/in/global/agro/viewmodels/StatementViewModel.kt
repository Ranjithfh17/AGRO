package `in`.global.agro.viewmodels

import `in`.global.agro.data.repo.StatementRepository
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class StatementViewModel @Inject constructor(private val repository: StatementRepository) :ViewModel(){

    fun getCustomerDetails(name: String)=repository.getCustomerDetails(name)
}