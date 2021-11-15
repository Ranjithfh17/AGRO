package `in`.global.agro.viewmodels

import `in`.global.agro.data.model.OTPModel
import `in`.global.agro.data.repo.OTPRepository
import `in`.global.agro.utils.Resource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OTPViewModel @Inject constructor(private val repository: OTPRepository) : ViewModel() {

    private val _otpFlow = MutableStateFlow<Resource<OTPModel>>(Resource.Loading())
    val otpFlow: StateFlow<Resource<OTPModel>>
        get() = _otpFlow


    fun getOTP(type: String, otp: String, s_type: String, led_id: String, latitude: String, longitude: String, token: String, device_id: String) {

        viewModelScope.launch {
            try {
                val res=repository.getOTP(type, otp, s_type, led_id, latitude, longitude, token, device_id)
                _otpFlow.value = Resource.Success(res)

            }catch (exception:Exception){
                _otpFlow.value = Resource.Error(exception.message.toString())
            }
        }


    }


}