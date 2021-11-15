package `in`.global.agro.ui.fragments.purchase

import `in`.global.agro.R
import `in`.global.agro.callbacks.TaxPopupCallback
import `in`.global.agro.data.model.CustomerBalanceModel
import `in`.global.agro.data.model.PurchaseModel
import `in`.global.agro.databinding.FragmentAddPurchaseBinding
import `in`.global.agro.extensions.showSnackBar
import `in`.global.agro.extensions.showToast
import `in`.global.agro.ui.popup.TaxPopup
import `in`.global.agro.viewmodels.PurchaseViewModel
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class AddPurchase : Fragment(R.layout.fragment_add_purchase) {
    private lateinit var binding: FragmentAddPurchaseBinding
    private val viewModel by activityViewModels<PurchaseViewModel>()
    private var xOff = 0F
    private var yOff = 0F
    private val productList = mutableListOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddPurchaseBinding.bind(view)

        setDate()
        addPurchase()
        showTaxPop()
        setTaxType()
        addAndNewPurchase()
        getAllProducts()

    }


    @SuppressLint("SimpleDateFormat")
    private fun setDate() {
        val currentCalender = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = simpleDateFormat.format(currentCalender.time)
        binding.date.text = currentDate


        binding.dateLayout.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(), { _, i, i1, i2 ->
                    if (month + 1 < 10) {
                        binding.date.text =
                            StringBuilder().append(i2).append("-").append("0").append(i1 + 1)
                                .append("-").append(i)
                    } else {
                        binding.date.text =
                            StringBuilder().append(i2).append("-").append(i1 + 1).append("-")
                                .append(i)
                    }
                }, year, month, day
            )
            datePickerDialog.show()
        }
    }

    private fun addPurchase() {
        binding.save.setOnClickListener {
            saveProduct()
        }

    }

    private fun saveProduct() {
        binding.apply {
            when {
                customerName.text.toString().isEmpty() -> {
                    requireContext().showToast("Enter customer name")
                }

                productName.text.toString().isEmpty() -> {
                    requireContext().showToast("Enter product name")
                }

                price.text.toString().isEmpty() -> {
                    requireContext().showToast("Enter product price")

                }

                quantity.text.toString().isEmpty() -> {
                    requireContext().showToast("Enter product quantity")
                }

                else -> {
                    lifecycleScope.launchWhenStarted {
                        viewModel.addPurchase(
                            PurchaseModel(
                                1,
                                binding.customerName.text.toString(),
                                binding.date.text.toString(),
                                binding.productName.text.toString(),
                                binding.quantity.text.toString(),
                                binding.price.text.toString(),
                                binding.discount.text.toString(),
                                binding.tax.text.toString(),
                                binding.totalAmount.text.toString()
                            )
                        )


                        viewModel.addCustomerBalance(
                            CustomerBalanceModel(
                                binding.customerName.text.toString(),
                                totalAmount.text.toString()
                            )
                        )


//                        viewModel.isCustomerExists(binding.customerName.text.toString()).collect {
//
//
//                        }



                        requireContext().showSnackBar(binding.root, "Purchase Added Successfully")


                        delay(300)
                        findNavController().popBackStack()
                    }

                }
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

    private fun addAndNewPurchase() {

        binding.saveAndNew.setOnClickListener {
            binding.apply {
                when {
                    customerName.text.toString().isEmpty() -> {
                        requireContext().showToast("Enter customer name")
                    }

                    productName.text.toString().isEmpty() -> {
                        requireContext().showToast("Enter product name")
                    }

                    price.text.toString().isEmpty() -> {
                        requireContext().showToast("Enter product price")

                    }

                    quantity.text.toString().isEmpty() -> {
                        requireContext().showToast("Enter product quantity")
                    }

                    else -> {
                        lifecycleScope.launchWhenStarted {
                            viewModel.addPurchase(
                                PurchaseModel(
                                    1,
                                    binding.customerName.text.toString(),
                                    binding.date.text.toString(),
                                    binding.productName.text.toString(),
                                    binding.quantity.text.toString(),
                                    binding.price.text.toString(),
                                    binding.discount.text.toString(),
                                    binding.tax.text.toString(),
                                    binding.totalAmount.text.toString()
                                )
                            )

                            viewModel.addCustomerBalance(
                                CustomerBalanceModel(
                                    binding.customerName.text.toString(),
                                    totalAmount.text.toString()
                                )
                            )

                            requireContext().showSnackBar(
                                binding.root,
                                "Purchase Added Successfully"
                            )

                            delay(100)

                            customerName.setText("")
                            productName.setText("")
                            quantity.setText("")
                            price.setText("")
                            discount.setText("")
                            tax.setText("")
                            totalAmount.setText("")
                        }

                    }
                }
            }
        }

    }

    private fun getAllProducts() {
        lifecycleScope.launchWhenStarted {
            viewModel.getAllProducts().collect {
                for (items in it) {
                    productList.add(items.productName)
                }
                val adapter = ArrayAdapter(requireContext(), R.layout.spinner_adapter, productList)
                binding.productName.setAdapter(adapter)
                binding.productName.threshold = 1

            }
        }
    }


}