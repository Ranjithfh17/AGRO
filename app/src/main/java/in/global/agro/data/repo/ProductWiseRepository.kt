package `in`.global.agro.data.repo

import `in`.global.agro.api.AgroApi
import javax.inject.Inject

class ProductWiseRepository @Inject constructor(private val agroApi: AgroApi) {

    suspend fun getPayList(type: String, cid: String) = agroApi.getPayList(type, cid)
}