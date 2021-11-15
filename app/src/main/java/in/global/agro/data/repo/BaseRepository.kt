package `in`.global.agro.data.repo

import `in`.global.agro.prefernces.MainPreference
import javax.inject.Inject

class BaseRepository @Inject constructor(private val mainPreference: MainPreference) {

    fun getUid() = mainPreference.getUid()

    fun getCid() = mainPreference.getCid()

    fun getUserName() = mainPreference.getUserName()

    fun getRoleId() = mainPreference.getRoleId()

    fun getMobileNo() = mainPreference.getMobileNo()

    fun getCompanyName() = mainPreference.getCompanyName()

    fun getCompanyAddress() = mainPreference.getCompanyAddress()

    fun getShortName() = mainPreference.getCompanyShortName()

    fun getMainId() = mainPreference.getMainId()


}