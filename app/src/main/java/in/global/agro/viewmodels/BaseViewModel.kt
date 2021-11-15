package `in`.global.agro.viewmodels

import `in`.global.agro.data.repo.BaseRepository
import `in`.global.agro.data.repo.LoginRepository
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class BaseViewModel @Inject constructor(private val repository: BaseRepository):ViewModel() {

    fun getUid() = repository.getUid()

    fun getCid() = repository.getCid()

    fun getUserName() = repository.getUserName()

    fun getRoleId() = repository.getRoleId()

    fun getMobileNo() = repository.getMobileNo()

    fun getCompanyName() = repository.getCompanyName()

    fun getCompanyAddress() = repository.getCompanyAddress()

    fun getShortName() = repository.getShortName()

    fun getMainId() = repository.getMainId()



}