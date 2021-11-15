package `in`.global.agro.ui.dialog

import `in`.global.agro.databinding.FragmentFilterProductsBinding
import `in`.global.agro.viewmodels.ProductViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FilterProducts : CustomBottomSheet() {

    private lateinit var binding: FragmentFilterProductsBinding
    private val viewModel by activityViewModels<ProductViewModel>()


    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterProductsBinding.inflate(inflater, container, false)

        setFilter()
        getFilterType()

        return binding.root

    }


    private fun setFilter() {
        binding.goods.setOnClickListener {
            viewModel.saveProductFilterType("Goods")
            dialog?.dismiss()

        }
        binding.services.setOnClickListener {
            viewModel.saveProductFilterType("Services")
            dialog?.dismiss()

        }
        binding.all.setOnClickListener {
            viewModel.saveProductFilterType("All")
            dialog?.dismiss()

        }

    }


    private fun getFilterType() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getProductFilterType().collect {
                when (it) {
                    "All" -> {
                        binding.all.isChecked = true
                    }


                    "Goods" -> {
                        binding.goods.isChecked = true
                    }


                    "Services" -> {
                        binding.services.isChecked = true
                    }

                }
            }
        }
    }


}