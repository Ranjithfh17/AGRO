package `in`.global.agro.ui.fragments.sales.dialog

import `in`.global.agro.R
import `in`.global.agro.callbacks.ProductItemCallback
import `in`.global.agro.callbacks.TaxPopupCallback
import `in`.global.agro.data.model.ProductItem
import `in`.global.agro.databinding.FragmentProductsDialogBinding
import `in`.global.agro.extensions.showToast
import `in`.global.agro.ui.popup.TaxPopup
import `in`.global.agro.viewmodels.SalesViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collect

class ProductsDialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentProductsDialogBinding
    private val viewModel by activityViewModels<SalesViewModel>()
    private var xOff = 0F
    private var yOff = 0F
    private val productList = mutableListOf<String>()
    private val priceList = mutableListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProductsDialogBinding.inflate(layoutInflater, container, false)

        showTaxPop()
        getAllProducts()

        saveProduct()


        return binding.root

    }


    @SuppressLint("ClickableViewAccessibility", "InflateParams")
    private fun showTaxPop() {

        binding.taxTypeLayout.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    xOff = event.x
                    yOff = event.y
                }

            }

            false

        }


        binding.taxTypeLayout.setOnClickListener {
            val taxPopup = TaxPopup(
                layoutInflater.inflate(R.layout.popup_tax, null),
                binding.taxTypeLayout,
                xOff, yOff
            )

            taxPopup.setOnTaxPopupCallback(object : TaxPopupCallback {
                override fun onTaxClick(value: String) {
                    Log.i("TAG", "onTaxClick: $value")
                    viewModel.setTaxType(value)
                    taxPopup.dismiss()
                    setTaxType()
                }
            })
        }
    }


    private fun setTaxType() {
        lifecycleScope.launchWhenStarted {
            viewModel.taxTypeFlow.collect {
                binding.taxType.text = it

                setCalculation(it)
            }
        }
    }


    private fun setCalculation(type: String) {

        binding.quantity.doOnTextChanged { _, _, _, _ ->

            try {

                binding.apply {
                    if (type == "Exclude Tax") {
                        val subtotal =
                            (quantity.text.toString()).toFloat() * (price.text.toString()).toFloat()
                        totalAmount.setText(subtotal.toString())
                    }

                    if (type == "Include Tax") {
                        val subtotal =
                            (quantity.text.toString()).toFloat() * (price.text.toString()).toFloat()
                        val taxPercentage = subtotal * (tax.text.toString()).toFloat() / 100
                        val total = taxPercentage + subtotal
                        totalAmount.setText(total.toString())
                    }
                }


            } catch (exception: Exception) {

            }


        }



        binding.price.doOnTextChanged { _, _, _, _ ->


            try {

                binding.apply {
                    if (type == "Exclude Tax") {
                        val subtotal =
                            (quantity.text.toString()).toFloat() * (price.text.toString()).toFloat()
                        totalAmount.setText(subtotal.toString())
                    }

                    if (type == "Include Tax") {
                        val subtotal =
                            (quantity.text.toString()).toFloat() * (price.text.toString()).toFloat()
                        val taxPercentage = subtotal * (tax.text.toString()).toFloat() / 100
                        val total = taxPercentage + subtotal
                        totalAmount.setText(total.toString())
                    }
                }


            } catch (exception: Exception) {

            }


        }



        binding.tax.doOnTextChanged { _, _, _, _ ->

            try {
                binding.apply {
                    if (type == "Exclude Tax") {
                        val subtotal =
                            (quantity.text.toString()).toFloat() * (price.text.toString()).toFloat()
                        totalAmount.setText(subtotal.toString())
                    }

                    if (type == "Include Tax") {
                        val subtotal =
                            (quantity.text.toString()).toFloat() * (price.text.toString()).toFloat()
                        val taxPercentage = subtotal * (tax.text.toString()).toFloat() / 100
                        val total = taxPercentage + subtotal
                        totalAmount.setText(total.toString())
                    }
                }


            } catch (exception: Exception) {

            }


        }


    }


    private fun getAllProducts() {
        lifecycleScope.launchWhenStarted {
            viewModel.getAllProducts().collect {

                for (i in it) {
                    productList.add(i.productName)
                    priceList.add(i.price)
                }

                val adapter = ArrayAdapter(requireContext(), R.layout.spinner_adapter, productList)
                binding.productName.setAdapter(adapter)
                binding.productName.threshold = 1

            }

        }

        binding.productName.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, _, _ ->

                for (i in 0 until productList.size) {
                    if (binding.productName.text.toString() == productList[i]) {
                        binding.price.setText(priceList[i])
                    }
                }


            }


    }


    private fun saveProduct() {

        binding.save.setOnClickListener {
            when {
                binding.productName.text.toString().isEmpty() -> {
                    requireContext().showToast("Enter product name")
                }
                binding.quantity.text.toString().isEmpty() -> {
                    requireContext().showToast("Enter product quantity")
                }
                binding.price.text.toString().isEmpty() -> {
                    requireContext().showToast("Enter product price")
                }
                else -> {

                    val productItem = ProductItem(
                        binding.productName.text.toString(),
                        binding.totalAmount.text.toString(),
                        binding.quantity.text.toString(),
                        binding.price.text.toString(),
                        binding.discount.text.toString(),
                        binding.tax.text.toString()
                    )

                    viewModel.addProductItem(productItem)
                    dialog?.dismiss()


                }
            }
        }


    }


    companion object {
        private lateinit var productItemCallback: ProductItemCallback

        @JvmName("setProductItemCallback1")
        fun setProductItemCallback(productItemCallback: ProductItemCallback) {
            Companion.productItemCallback = productItemCallback
        }
    }


}