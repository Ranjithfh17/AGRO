package `in`.global.agro.ui.fragments

import `in`.global.agro.R
import `in`.global.agro.databinding.FragmentCustomerStatementBinding
import `in`.global.agro.extensions.showToast
import `in`.global.agro.ui.adapter.CustomerStatementAdapter
import `in`.global.agro.viewmodels.StatementViewModel
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CustomerStatement : Fragment(R.layout.fragment_customer_statement) {

    private lateinit var binding: FragmentCustomerStatementBinding
    private val viewModel by viewModels<StatementViewModel>()
    private lateinit var customerStatementAdapter: CustomerStatementAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCustomerStatementBinding.bind(view)

        setStartDate()
        setEndDate()
        setUpRecyclerView()

        binding.search.setOnClickListener {
            if (binding.searchView.text.toString().isEmpty()) {
                requireContext().showToast("Enter username")
            } else {
                search()
            }
        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun setStartDate() {
        val currentCalender = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = simpleDateFormat.format(currentCalender.time)
        binding.startDate.text = currentDate


        binding.startDateLayout.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(), { _, i, i1, i2 ->
                    if (month + 1 < 10) {
                        binding.startDate.text =
                            StringBuilder().append(i2).append("-").append("0").append(i1 + 1)
                                .append("-").append(i)
                    } else {
                        binding.startDate.text =
                            StringBuilder().append(i2).append("-").append(i1 + 1).append("-")
                                .append(i)
                    }
                }, year, month, day
            )
            datePickerDialog.show()
        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun setEndDate() {
        val currentCalender = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = simpleDateFormat.format(currentCalender.time)
        binding.endDate.text = currentDate


        binding.endDateLayout.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(), { _, i, i1, i2 ->
                    if (month + 1 < 10) {
                        binding.endDate.text =
                            StringBuilder().append(i2).append("-").append("0").append(i1 + 1)
                                .append("-").append(i)
                    } else {
                        binding.endDate.text =
                            StringBuilder().append(i2).append("-").append(i1 + 1).append("-")
                                .append(i)
                    }
                }, year, month, day
            )
            datePickerDialog.show()
        }
    }


    private fun setUpRecyclerView() {
        customerStatementAdapter = CustomerStatementAdapter()
        binding.customerStatementRecyclerView.apply {
            adapter = customerStatementAdapter
        }
    }


    private fun search() {
        lifecycleScope.launchWhenStarted {
            viewModel.getCustomerDetails(binding.searchView.text.toString()).collect {
                customerStatementAdapter.differ.submitList(it)
            }
        }
    }



}