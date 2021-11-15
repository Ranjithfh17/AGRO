package `in`.global.agro.api

import `in`.global.agro.data.model.*
import retrofit2.http.*

interface AgroApi {

    @GET("index.php")
    suspend fun login(
        @Query("type") type: String,
        @Query("mobile") mobileNo: String,
        @Query("tok") signature: String,
        @Query("token") token: String,
        @Query("device_id") deviceId: String
    ): Login


    @GET("index.php")
    suspend fun getOTP(
        @Query("type") type: String,
        @Query("otp") otp: String,
        @Query("stype") s_type: String,
        @Query("led_id") led_id: String,
        @Query("lt") latitude: String,
        @Query("ln") longitude: String,
        @Query("token") token: String,
        @Query("device_id") device_id: String
    ): OTPModel


    @FormUrlEncoded
    @POST("index.php")
    suspend fun getStatusList(
        @Field("type") type: String,
        @Field("device_id") device_id: String,
        @Field("uid") cid: String,
        @Field("cid") uid: String,
        @Field("role_id") role_id: String
    ): HomeModel


    @POST("newapi.php?type=5")
    suspend fun addSales(
        @Body salesModel: SalesModel
    )


    @FormUrlEncoded
    @POST("receipt_api.php")
    suspend fun getPayList(
        @Field("type") type: String,
        @Field("cid") cid: String,
    ):List<PayTypeItem>


}