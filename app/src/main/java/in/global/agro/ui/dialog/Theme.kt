package `in`.global.agro.ui.dialog

import `in`.global.agro.databinding.FragmentThemeBinding
import `in`.global.agro.utils.ThemeSetter
import `in`.global.agro.viewmodels.SettingsViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect


class Theme : CustomBottomSheet() {
    private lateinit var binding:FragmentThemeBinding
    private val viewModel by activityViewModels<SettingsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentThemeBinding.inflate(inflater,container,false)

        setTheme()

        getAppTheme()


        return binding.root

    }

    private fun setTheme(){
        binding.lightTheme.setOnClickListener {
            saveTheme("lightTheme")
            dialog?.dismiss()

        }
        binding.darKTheme.setOnClickListener {
            saveTheme("darkTheme")
            dialog?.dismiss()


        }
        binding.followSystem.setOnClickListener {
            saveTheme("followSystem")
            dialog?.dismiss()


        }
        binding.auto.setOnClickListener {
            saveTheme("auto")
            dialog?.dismiss()


        }
    }

    private fun saveTheme(theme:String){
        viewModel.setAppTheme(theme)
        getAppTheme()


    }

    private fun getAppTheme(){
        lifecycleScope.launchWhenStarted {
            viewModel.getAppTheme().collect { themeMode ->
                Log.i("TAG", "getAppTheme:$themeMode ")

                ThemeSetter.setAppTheme(themeMode)

                binding.lightTheme.isChecked = themeMode == "lightTheme"
                binding.darKTheme.isChecked = themeMode == "darkTheme"
                binding.followSystem.isChecked = themeMode == "followSystem"
                binding.auto.isChecked = themeMode == "auto"


            }
        }
    }





}