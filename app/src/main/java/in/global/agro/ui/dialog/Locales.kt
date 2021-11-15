package `in`.global.agro.ui.dialog

import `in`.global.agro.callbacks.LocaleCallback
import `in`.global.agro.databinding.FragmentLanguageBinding
import `in`.global.agro.viewmodels.SettingsViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Locales : CustomBottomSheet() {

    private lateinit var binding: FragmentLanguageBinding
    private val viewModel by activityViewModels<SettingsViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLanguageBinding.inflate(inflater, container, false)



        binding.tamil.setOnClickListener {
            viewModel.setAppLanguage("ta")
            Lingver.getInstance().setLocale(requireContext(),"ta")
            requireActivity().recreate()
        }


        binding.english.setOnClickListener {
            viewModel.setAppLanguage("en")
            Lingver.getInstance().setLocale(requireContext(),"en")
            requireActivity().recreate()
        }




        return binding.root

    }








}