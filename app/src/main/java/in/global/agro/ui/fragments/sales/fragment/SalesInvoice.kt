package `in`.global.agro.ui.fragments.sales.fragment

import `in`.global.agro.R
import `in`.global.agro.callbacks.TaxPopupCallback
import `in`.global.agro.data.model.SalesInvoiceModel
import `in`.global.agro.databinding.FragmentSalesInvoiceBinding
import `in`.global.agro.extensions.showToast
import `in`.global.agro.ui.popup.TaxPopup
import `in`.global.agro.viewmodels.SalesViewModel
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*


class SalesInvoice : Fragment(R.layout.fragment_sales_invoice) {

    private lateinit var binding:FragmentSalesInvoiceBinding
    private var xOff = 0F
    private var yOff = 0F
    private val viewModel by activityViewModels<SalesViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentSalesInvoiceBinding.bind(view)

        setDate()
        showTaxPop()
        setTaxType()
        navigateToPreview()
        navigateToHome()

    }


    private fun setCalculation(type:String) {

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
                    if (type== "Exclude Tax") {
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


    private fun navigateToPreview(){
        binding.save.setOnClickListener {
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
                        val salesInvoiceModel=SalesInvoiceModel(
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

                        val action=SalesInvoiceDirections.actionSalesInvoiceToSalesInvoicePreview(salesInvoiceModel)
                        findNavController().navigate(action)

                    }
                }
            }
        }
    }

    private fun navigateToHome(){
        binding.cancel.setOnClickListener {
            findNavController().popBackStack(R.id.home3,true)
        }
    }

}