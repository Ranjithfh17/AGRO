package `in`.global.agro.data.repo

import `in`.global.agro.api.AgroApi
import retrofit2.http.Query
import javax.inject.Inject

class HomeRepository @Inject constructor(private val agroApi: AgroApi) {

    suspend fun getStatusList(
        type: String,
        device_id: String,
        uid: String,
        cid: String,
        role_id: String
    ) =agroApi.getStatusList(type, device_id, uid,cid, role_id)

}