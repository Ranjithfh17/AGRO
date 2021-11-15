package `in`.global.agro.prefernces

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsPreference(private val context: Context) {

    companion object {
        const val PREF_NAME = "Settings"

        const val LANGUAGE = "language"
        const val THEME = "theme"
        const val KEEP_SCREEN_ON = "keep screen on"
    }


    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREF_NAME)


    suspend fun setAppLanguage(value: String) {
        val dataStoreKey = stringPreferencesKey(LANGUAGE)
        context.dataStore.edit {
            it[dataStoreKey] = value
        }
    }


    fun getAppLanguage(): Flow<String> = context.dataStore.data.map {
        val dataStoreKey = stringPreferencesKey(LANGUAGE)
        val language = it[dataStoreKey] ?: "default"
        language
    }

    suspend fun setAppTheme(value: String) {
        val dataStoreKey = stringPreferencesKey(THEME)
        context.dataStore.edit {
            it[dataStoreKey] = value
        }
    }

    fun getAppTheme(): Flow<String> = context.dataStore.data.map {
        val dataStoreKey = stringPreferencesKey(THEME)
        val theme = it[dataStoreKey] ?: "followSystem"
        theme
    }

    suspend fun setKeepScreenOn(value: Boolean) {
        val dataStoreKey = booleanPreferencesKey(KEEP_SCREEN_ON)
        context.dataStore.edit {
            it[dataStoreKey] = value
        }
    }

    fun isKeepScreenEnabled():Flow<Boolean> = context.dataStore.data.map {
        val dataStoreKey = booleanPreferencesKey(KEEP_SCREEN_ON)
        val isEnabled=it[dataStoreKey] ?: false
        isEnabled
    }


}