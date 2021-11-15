package `in`.global.agro.ui.fragments.receipt

import `in`.global.agro.R
import `in`.global.agro.databinding.FragmentReceiptBinding
import `in`.global.agro.ui.adapter.ReceiptAdapter
import `in`.global.agro.viewmodels.ReceiptViewModel
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class Receipt : Fragment(R.layout.fragment_receipt) {

    private val viewModel by activityViewModels<ReceiptViewModel>()
    private lateinit var binding: FragmentReceiptBinding
    private lateinit var receiptAdapter: ReceiptAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReceiptBinding.bind(view)


        setUpRecyclerView()
        getAllReceipts()
        addReceipt()


    }



    private fun addReceipt() {
        binding.addReceipt.setOnClickListener {
            findNavController().navigate(R.id.receiptFragment)
        }
    }


    private fun setUpRecyclerView() {
        receiptAdapter = ReceiptAdapter()
        binding.receiptRecyclerView.apply {
            adapter = receiptAdapter
            layoutManager= LinearLayoutManager(requireContext())

        }
    }


    private fun getAllReceipts() {
        lifecycleScope.launchWhenStarted {
            viewModel.getAllReceipt().collect {
                Log.i("TAG", "getAllReceipts: $it")
                    receiptAdapter.differ.submitList(it)
            }
        }
    }


}