package `in`.global.agro.data.repo

import `in`.global.agro.api.AgroApi
import `in`.global.agro.prefernces.MainPreference
import javax.inject.Inject

class LoginRepository @Inject constructor(private val agroApi: AgroApi,private val mainPreference: MainPreference) {

    suspend fun login(
        type: String,
        mobileNo: String,
        signature: String,
        token: String,
        deviceId: String
    ) = agroApi.login(type, mobileNo, signature, token, deviceId)


    suspend fun saveToken(value:String)=mainPreference.saveToken(value)

    fun getToken()=mainPreference.getToken()


}