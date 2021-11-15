package `in`.global.agro.ui.fragments

import `in`.global.agro.R
import `in`.global.agro.callbacks.LocaleCallback
import `in`.global.agro.databinding.FragmentSettingsBinding
import `in`.global.agro.ui.dialog.Locales
import `in`.global.agro.viewmodels.SettingsViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import kotlinx.coroutines.flow.collect
import java.util.*


class Settings : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel by activityViewModels<SettingsViewModel>()
    private lateinit var appVersion:String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)


        setTheme()
        setThemeMode()
        setIcon()
        showAppLocaleDialog()
        getAppVersion()
        checkAppUpdate()
        setKeepScreenOn()
        isScreenEnabled()
        getAppLanguageCode()


    }



    private fun setTheme() {
        binding.themeLayout.setOnClickListener {
            findNavController().navigate(R.id.theme)
        }
    }


    private fun setThemeMode() {

        lifecycleScope.launchWhenStarted {
            viewModel.getAppTheme().collect { themeMode ->
                when (themeMode) {
                    "lightTheme" -> {
                        binding.themeMode.text = getString(R.string.lightTheme)
                    }
                    "darkTheme" -> {
                        binding.themeMode.text = getString(R.string.darkTheme)
                    }

                    "followSystem" -> {
                        binding.themeMode.text = getString(R.string.followSystem)
                    }
                    "auto" -> {
                        binding.themeMode.text = getString(R.string.auto)
                    }
                }

            }
        }

    }


    private fun setIcon() {
        binding.iconLayout.setOnClickListener {
            findNavController().navigate(R.id.icons)
        }
    }


    private fun showAppLocaleDialog() {
        binding.languageLayout.setOnClickListener {
            findNavController().navigate(R.id.language)
        }
    }

    private fun getAppVersion(){
        try {
            val packageManager=requireContext().packageManager.getPackageInfo(requireContext().packageName,0)
            appVersion=packageManager.versionName
            binding.appVersion.text=appVersion
        }catch (exception:Exception){
            /*no_op*/
        }
    }


    private fun checkAppUpdate(){
        binding.appUpdateLayout.setOnClickListener {
            val appUpdateManager = AppUpdateManagerFactory.create(requireContext())
            val appUpdateInfo = appUpdateManager.appUpdateInfo

            appUpdateInfo.addOnSuccessListener { updateInfo ->
                if (updateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && updateInfo.isUpdateTypeAllowed(
                        AppUpdateType.FLEXIBLE)) {
                    appUpdateManager.startUpdateFlowForResult(updateInfo, AppUpdateType.FLEXIBLE, requireActivity(), 1001)
                } else {
                    Toast.makeText(requireContext(), "No Update Available", Toast.LENGTH_SHORT).show()
                }
            }

            appUpdateInfo.addOnFailureListener{
                Log.i("TAG", "checkAppUpdate:${it.localizedMessage} ")
            }
        }

    }

    private fun setKeepScreenOn(){
        lifecycleScope.launchWhenStarted {

            binding.screenSwitch.setOnClickListener {

                if(binding.screenSwitch.isChecked){
                    viewModel.setKeepScreenOn(true)
                    isScreenEnabled()

                }else{
                    viewModel.setKeepScreenOn(false)
                    isScreenEnabled()

                }
            }

        }
    }


    private fun isScreenEnabled(){
        lifecycleScope.launchWhenStarted {
            viewModel.isScreenEnabled().collect { enabled ->
                Log.i("TAG", "isScreenEnabled: $enabled")
                if (enabled){
                    binding.screenSwitch.isChecked=true
                    requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }else{
                    binding.screenSwitch.isChecked=false
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }
            }
        }
    }






    @SuppressLint("SetTextI18n")
    private fun getAppLanguageCode(){
        lifecycleScope.launchWhenStarted {
            viewModel.getAppLanguage().collect {
                when(it){
                    "default" ->{
                        binding.language.text="System Default"
                    }
                    "en" ->{
                        binding.language.text="English"
                    }
                    "ta" ->{
                        binding.language.text="Tamil"
                    }
                }
            }
        }
    }



}