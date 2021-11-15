package `in`.global.agro

import `in`.global.agro.utils.LocaleHelper
import android.app.Application
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class AgroApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        Lingver.init(this,LocaleHelper.getSystemLanguageCode())
    }

}
