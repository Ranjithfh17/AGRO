package `in`.global.agro.viewmodels

import `in`.global.agro.data.model.HomeModel
import `in`.global.agro.data.model.Login
import `in`.global.agro.data.model.Test
import `in`.global.agro.data.repo.HomeRepository
import `in`.global.agro.utils.Resource
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    private val _statusFlow = MutableStateFlow<Resource<List<HomeModel.Data>>>(Resource.Loading())
    val statusFlow: StateFlow<Resource<List<HomeModel.Data>>>
        get() = _statusFlow






    fun getStatusList(
        type: String,
        device_id: String,
        uid: String,
        cid: String,
        role_id: String
    ) {
        viewModelScope.launch {
            try {
                val response = repository.getStatusList(type, device_id, uid, cid, role_id)
                _statusFlow.value=Resource.Success(response.data)
            }catch (exception:Exception){
                _statusFlow.value=Resource.Error(exception.message.toString())
            }
        }
    }
}