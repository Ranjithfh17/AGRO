package `in`.global.agro.viewmodels

import `in`.global.agro.data.repo.LoginRepository
import `in`.global.agro.data.model.Login
import `in`.global.agro.utils.Resource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject




@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    private val _loginFlow = MutableStateFlow<Resource<Login>>(Resource.Loading())
    val loginFlow: StateFlow<Resource<Login>>
        get() = _loginFlow


    fun login(type: String, mobileNo: String, signature: String, token: String, deviceId: String) {
        viewModelScope.launch {
            try {

                val response = repository.login(type, mobileNo, signature, token, deviceId)
                _loginFlow.value = Resource.Success(response)

            } catch (exception: Exception) {
                _loginFlow.value = Resource.Error(exception.message.toString())

            }
        }

    }



    fun saveToken(value:String)=viewModelScope.launch {
        repository.saveToken(value)
    }


    fun getToken()=repository.getToken()




}