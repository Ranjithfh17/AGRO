package `in`.global.agro.data.repo

import `in`.global.agro.prefernces.SettingsPreference
import javax.inject.Inject

class SettingRepository @Inject constructor(private val settingsPreference: SettingsPreference) {

    suspend fun setAppLanguage(language: String) = settingsPreference.setAppLanguage(language)

    fun getAppLanguage() = settingsPreference.getAppLanguage()


    suspend fun setAppTheme(theme: String) = settingsPreference.setAppTheme(theme)

    fun getAppTheme() = settingsPreference.getAppTheme()

    suspend fun setKeepScreenOn(value:Boolean)=settingsPreference.setKeepScreenOn(value)

    fun isScreenOnEnabled()=settingsPreference.isKeepScreenEnabled()
}