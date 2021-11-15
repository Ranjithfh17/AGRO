package `in`.global.agro.ui.activity

import `in`.global.agro.utils.*
import `in`.global.agro.viewmodels.BaseViewModel
import `in`.global.agro.viewmodels.SettingsViewModel
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {


    private val viewModel by viewModels<SettingsViewModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setAppTheme()
        isScreenEnabled()

    }


    private fun setAppTheme() {
        lifecycleScope.launchWhenStarted {
            viewModel.getAppTheme().collect {
                ThemeSetter.setAppTheme(it)
            }
        }

    }


    private fun isScreenEnabled() {
        lifecycleScope.launchWhenStarted {
            viewModel.isScreenEnabled().collect { enabled ->
                if (enabled) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }
            }
        }
    }

}