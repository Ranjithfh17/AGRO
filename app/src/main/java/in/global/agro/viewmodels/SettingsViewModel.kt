package `in`.global.agro.viewmodels

import `in`.global.agro.data.repo.SettingRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: SettingRepository) : ViewModel() {

    fun setAppLanguage(language:String)=viewModelScope.launch {
        repository.setAppLanguage(language)
    }

    fun getAppLanguage()=repository.getAppLanguage()

    fun setAppTheme(theme:String)=viewModelScope.launch {
        repository.setAppTheme(theme)
    }

    fun getAppTheme()=repository.getAppTheme()

    fun setKeepScreenOn(value:Boolean) = viewModelScope.launch {
        repository.setKeepScreenOn(value)
    }

    fun isScreenEnabled()=repository.isScreenOnEnabled()


}