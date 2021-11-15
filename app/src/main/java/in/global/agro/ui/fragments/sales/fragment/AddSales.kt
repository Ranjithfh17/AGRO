package `in`.global.agro.ui.fragments.sales.fragment

import `in`.global.agro.R
import `in`.global.agro.data.model.*
import `in`.global.agro.databinding.FragmentAddSalesBinding
import `in`.global.agro.extensions.showToast
import `in`.global.agro.ui.fragments.sales.adapter.ProductItemAdapter
import `in`.global.agro.viewmodels.SalesViewModel
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class AddSales : Fragment(R.layout.fragment_add_sales) {


    private lateinit var binding: FragmentAddSalesBinding
    private val viewModel by activityViewModels<SalesViewModel>()
    private val args by navArgs<AddSalesArgs>()
    private lateinit var productItemAdapter: ProductItemAdapter
    private var productList: ArrayList<Products> = arrayListOf()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddSalesBinding.bind(view)

        setDate()
        addSales()
        addAndNewSales()
        showDialog()

        setUpRecyclerView()

        getProductItems()


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

    private fun addSales() {
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


                else -> {

                }
            }
        }

    }


    private fun showDialog() {
        binding.addProducts.setOnClickListener {
            findNavController().navigate(R.id.productsDialog)
        }
    }


    private fun setUpRecyclerView() {
        productItemAdapter = ProductItemAdapter()
        binding.productItemRecyclerView.apply {
            adapter = productItemAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }


    private fun getProductItems() {
        lifecycleScope.launchWhenStarted {
            viewModel.getProductItem().collect {
                productItemAdapter.differ.submitList(it)


                productList.clear()


                Log.i("TAG", "getProductItems: $it")
                for (i in it) {


                    productList.add(
                        Products(
                            i.product,
                            i.subtotal,
                            i.quantity,
                            i.price,
                            i.discount,
                            i.tax
                        )
                    )


                }
            }
        }
    }


    private fun addAndNewSales() {
        binding.saveAndNew.setOnClickListener {
            binding.apply {
                when {
                    customerName.text.toString().isEmpty() -> {
                        requireContext().showToast("Enter customer name")
                    }

                    else -> {

                        lifecycleScope.launchWhenStarted {
                            viewModel.addSales(
                                SalesModel(
                                    binding.customerName.text.toString(),
                                    binding.date.text.toString(),
                                    productList
                                )
                            )
                        }

                        viewModel.deleteProducts()


                    }
                }
            }
        }

    }


}