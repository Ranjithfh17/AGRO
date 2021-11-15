package `in`.global.agro.ui.fragments

import `in`.global.agro.R
import `in`.global.agro.data.model.CustomerBalanceModel
import `in`.global.agro.databinding.FragmentCustomerBalanceBinding
import `in`.global.agro.ui.adapter.CustomerBalanceAdapter
import `in`.global.agro.viewmodels.CustomerBalanceViewModel
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class CustomerBalance : Fragment(R.layout.fragment_customer_balance) {

    private lateinit var binding: FragmentCustomerBalanceBinding
    private val viewModel by viewModels<CustomerBalanceViewModel>()
    private lateinit var customerBalanceAdapter: CustomerBalanceAdapter
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCustomerBalanceBinding.bind(view)

        setRecyclerView()
        getBalanceList()

    }


    private fun setRecyclerView() {
        customerBalanceAdapter = CustomerBalanceAdapter()
        binding.customerBalanceRecyclerView.apply {
            adapter = customerBalanceAdapter
        }
    }


    private fun getBalanceList(){
        lifecycleScope.launchWhenStarted {
//            viewModel.getAllBalanceList().collect {
//                customerBalanceAdapter.differ.submitList(it)
//            }
        }
    }


}