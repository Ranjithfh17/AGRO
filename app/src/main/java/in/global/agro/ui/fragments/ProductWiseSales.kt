package `in`.global.agro.ui.fragments

import `in`.global.agro.R
import `in`.global.agro.databinding.FragmentProductWiseSalesBinding
import `in`.global.agro.utils.Resource
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import `in`.global.agro.viewmodels.ProductWiseViewModel
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class ProductWiseSales : Fragment(R.layout.fragment_product_wise_sales) {

    private lateinit var binding:FragmentProductWiseSalesBinding
    private val viewModel by viewModels<ProductWiseViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentProductWiseSalesBinding.bind(view)

        setCategoryItem()
        setDate()
    }



    private fun setCategoryItem(){
        val categoryList= mutableListOf<String>()

        lifecycleScope.launchWhenStarted {
            viewModel.payFlow.collect {
                when(it){
                    is Resource.Loading->{

                    }
                    is Resource.Error->{
                        Log.i("TAG", "setCategoryItem: ${it.message}")

                    }
                    is Resource.Success->{

                        for (items in it.data!!){
                            categoryList.add(items.name)
                        }
                        Log.i("TAG", "setCategoryItem: ${it.data}")
                    }
                }
            }

        }

        val arrayAdapter=ArrayAdapter(requireContext(),R.layout.spinner_adapter,categoryList)
        binding.categorySpinner.setAdapter(arrayAdapter)
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



}