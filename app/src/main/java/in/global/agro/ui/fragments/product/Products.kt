package `in`.global.agro.ui.fragments.product

import `in`.global.agro.R
import `in`.global.agro.callbacks.ProductCallback
import `in`.global.agro.data.model.ProductModel
import `in`.global.agro.databinding.FragmentProductsBinding
import `in`.global.agro.extensions.showSnackBar
import `in`.global.agro.ui.adapter.ProductAdapter
import `in`.global.agro.viewmodels.ProductViewModel
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class Products : Fragment(R.layout.fragment_products), ProductCallback {

    private lateinit var binding: FragmentProductsBinding
    private val viewModel by activityViewModels<ProductViewModel>()
    private lateinit var productAddProduct: ProductAdapter
    private lateinit var productList: List<ProductModel>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductsBinding.bind(view)

        setUpRecyclerView()
        addProduct()
        showFilterDialog()
        getFilterType()
        searchProduct()

    }


    private fun showFilterDialog() {
        binding.filterProducts.setOnClickListener {
            findNavController().navigate(R.id.filterProducts)
        }
    }


    private fun addProduct() {
        binding.addProduct.setOnClickListener {
            findNavController().navigate(R.id.addProduct)
        }
    }


    private fun setUpRecyclerView() {
        productAddProduct = ProductAdapter(this)
        binding.productRecyclerView.apply {
            adapter = productAddProduct
        }
    }


    private fun getProductList() {
        lifecycleScope.launchWhenStarted {
            viewModel.getAllProducts().collect {
                productAddProduct.differ.submitList(it)
                productList = it
            }
        }
    }


    private fun getProductsCategory(category: String) {
        lifecycleScope.launchWhenStarted {
            viewModel.getProductCategory(category).collect {
                productAddProduct.differ.submitList(it)
            }
        }
    }


    override fun onDeleteProduct(productModel: ProductModel) {
        lifecycleScope.launchWhenStarted {
            viewModel.deleteProduct(productModel)
            requireContext().showSnackBar(binding.root, "Product deleted successfully")
        }

    }


    private fun getFilterType() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getProductFilterType().collect {
                when (it) {
                    "All" -> {
                        getProductList()
                    }
                    "Goods" -> {
                        getProductsCategory("GOODS")
                    }
                    "Services" -> {
                        getProductsCategory("SERVICE")
                    }
                }
            }
        }
    }

    private fun searchProduct(){
        binding.searchView.doOnTextChanged { text, _, _, _ ->
            filterProduct(text.toString())

        }
    }


    private fun filterProduct(name: String) {
        val searchList = mutableListOf<ProductModel>()
        for (products in productList) {
            if (products.productName.lowercase().contains(name)) {
                searchList.add(products)
            }
        }
        productAddProduct.differ.submitList(searchList)
    }


}
