package `in`.global.agro.data.repo

import `in`.global.agro.api.AgroApi
import `in`.global.agro.data.model.OTPModel
import javax.inject.Inject


class OTPRepository @Inject constructor(private val agroApi: AgroApi) {


    suspend fun getOTP(
        type: String,
        otp: String,
        s_type: String,
        led_id: String,
        latitude: String,
        longitude: String,
        token: String,
        device_id: String
    ) =agroApi.getOTP(type, otp, s_type, led_id, latitude, longitude, token, device_id)



}