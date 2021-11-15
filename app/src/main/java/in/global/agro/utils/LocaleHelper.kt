package `in`.global.agro.utils

import android.content.res.Resources

object LocaleHelper {



    fun getSystemLanguageCode(): String {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Resources.getSystem().configuration.locales[0].language
        } else {
            @Suppress("deprecation")
            Resources.getSystem().configuration.locale.language
        }
    }


}