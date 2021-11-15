package `in`.global.agro.prefernces

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



class MainPreference(val context: Context) {

    companion object{
        const val PREF_NAME="main_pref"
        const val IS_LOGGED_IN="is_loggedIn"
        const val TOKEN="token"
        const val UID="uid"
        const val CID="cid"
        const val UNAME="uname"
        const val ROLE_ID="role_id"
        const val MOBILE="mobile"
        const val COMPANY_NAME="company"
        const val COMPANY_ADDRESS="company_address"
        const val SHORT_NAME="short_name"
        const val MAIN_ID="main_id"
        const val FILTER_PRODUCT="filter_product"
    }


    private val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(PREF_NAME)


    suspend fun setLogin(value:Boolean){
        val dataStoreKey= booleanPreferencesKey(IS_LOGGED_IN)
        context.dataStore.edit {
            it[dataStoreKey] = value
        }
    }


    fun isLoggedIn():Flow<Boolean> = context.dataStore.data.map {
        val dataStoreKey= booleanPreferencesKey(IS_LOGGED_IN)
        val isLoggedIn=it[dataStoreKey] ?: false
        isLoggedIn

    }

    suspend fun saveToken(value: String){
        val dataStoreKey= stringPreferencesKey(TOKEN)
        context.dataStore.edit {
            it[dataStoreKey] = value
        }
    }

    fun getToken():Flow<String> = context.dataStore.data.map {
        val dataStoreKey= stringPreferencesKey(TOKEN)
        val token=it[dataStoreKey] ?:  "failed to get token"
        token

    }

    suspend fun saveUid(value: String){
        val dataStoreKey= stringPreferencesKey(UID)
        context.dataStore.edit {
            it[dataStoreKey] = value
        }
    }


    fun getUid():Flow<String> = context.dataStore.data.map {
        val dataStoreKey= stringPreferencesKey(UID)
        val uid=it[dataStoreKey] ?:  "failed to get Uid"
        uid
    }



    suspend fun saveCid(value: String){
        val dataStoreKey= stringPreferencesKey(CID)
        context.dataStore.edit {
            it[dataStoreKey] = value
        }
    }


    fun getCid():Flow<String> = context.dataStore.data.map {
        val dataStoreKey= stringPreferencesKey(CID)
        val cid=it[dataStoreKey] ?:  "failed to get Cid"
        cid
    }


    suspend fun saveUserName(value: String){
        val dataStoreKey= stringPreferencesKey(UNAME)
        context.dataStore.edit {
            it[dataStoreKey] = value
        }
    }


    fun getUserName():Flow<String> = context.dataStore.data.map {
        val dataStoreKey= stringPreferencesKey(UNAME)
        val userName=it[dataStoreKey] ?:  "failed to get userName"
        userName
    }


    suspend fun saveRoleId(value: String){
        val dataStoreKey= stringPreferencesKey(ROLE_ID)
        context.dataStore.edit {
            it[dataStoreKey] = value
        }
    }

    fun getRoleId():Flow<String> = context.dataStore.data.map {
        val dataStoreKey= stringPreferencesKey(ROLE_ID)
        val roleId=it[dataStoreKey] ?:  "failed to get roleId"
        roleId
    }

    suspend fun saveMobileNo(value: String){
        val dataStoreKey= stringPreferencesKey(MOBILE)
        context.dataStore.edit {
            it[dataStoreKey] = value
        }
    }


    fun getMobileNo():Flow<String> = context.dataStore.data.map {
        val dataStoreKey= stringPreferencesKey(MOBILE)
        val mobileNo=it[dataStoreKey] ?:  "failed to get mobileNo"
        mobileNo
    }


    suspend fun saveCompanyName(value: String){
        val dataStoreKey= stringPreferencesKey(COMPANY_NAME)
        context.dataStore.edit {
            it[dataStoreKey] = value
        }
    }


    fun getCompanyName():Flow<String> = context.dataStore.data.map {
        val dataStoreKey= stringPreferencesKey(COMPANY_NAME)
        val companyName=it[dataStoreKey] ?:  "failed to get company_name"
        companyName
    }

    suspend fun saveCompanyAddress(value: String){
        val dataStoreKey= stringPreferencesKey(COMPANY_ADDRESS)
        context.dataStore.edit {
            it[dataStoreKey] = value
        }
    }


    fun getCompanyAddress():Flow<String> = context.dataStore.data.map {
        val dataStoreKey= stringPreferencesKey(COMPANY_ADDRESS)
        val companyAddress=it[dataStoreKey] ?:  "failed to get company_address"
        companyAddress
    }

    suspend fun saveShortName(value: String){
        val dataStoreKey= stringPreferencesKey(SHORT_NAME)
        context.dataStore.edit {
            it[dataStoreKey] = value
        }
    }


    fun getCompanyShortName():Flow<String> = context.dataStore.data.map {
        val dataStoreKey= stringPreferencesKey(SHORT_NAME)
        val shortName=it[dataStoreKey] ?:  "failed to get shortName"
        shortName
    }


    suspend fun saveMainId(value: String){
        val dataStoreKey= stringPreferencesKey(MAIN_ID)
        context.dataStore.edit {
            it[dataStoreKey] = value
        }
    }


    fun getMainId():Flow<String> = context.dataStore.data.map {
        val dataStoreKey= stringPreferencesKey(MAIN_ID)
        val mainId=it[dataStoreKey] ?:  "failed to get MainID"
        mainId
    }

    suspend fun saveProductFilterTYpe(value: String){
        val dataStoreKey= stringPreferencesKey(FILTER_PRODUCT)
        context.dataStore.edit {
            it[dataStoreKey]=value
        }
    }


    fun getProductFilterType():Flow<String> = context.dataStore.data.map {
        val dataStoreKey= stringPreferencesKey(FILTER_PRODUCT)
        val type=it[dataStoreKey] ?: "All"
        type
    }



}