package `in`.global.agro.ui.fragments.product

import `in`.global.agro.R
import `in`.global.agro.data.model.ProductModel
import `in`.global.agro.databinding.FragmentAddProductBinding
import `in`.global.agro.extensions.showToast
import `in`.global.agro.viewmodels.ProductViewModel
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProduct : Fragment(R.layout.fragment_add_product) {

    private lateinit var binding: FragmentAddProductBinding
    private val viewModel by activityViewModels<ProductViewModel>()
    private lateinit var categoryList: MutableList<String>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddProductBinding.bind(view)

        setCateGoryList()
        addProduct()


    }


    private fun addProduct() {
        binding.addProduct.setOnClickListener {
            when {
                binding.product.text.toString().isEmpty() -> {
                    requireContext().showToast("Enter Product Name")
                }
                binding.price.text.toString().isEmpty() -> {
                    requireContext().showToast("Enter Product Price")
                }
                else -> {
                    viewModel.addProduct(
                        ProductModel(
                            binding.product.text.toString(),
                            binding.price.text.toString(),
                            binding.categorySpinner.text.toString()
                        )
                    )
                    findNavController().popBackStack()
                }
            }
        }
    }




    private fun setCateGoryList() {
        categoryList = mutableListOf(
            "GOODS",
            "SERVICE"
        )

        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_adapter, categoryList)
        binding.categorySpinner.setAdapter(adapter)
    }



}