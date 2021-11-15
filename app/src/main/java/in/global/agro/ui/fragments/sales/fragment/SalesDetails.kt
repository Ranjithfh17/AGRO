package `in`.global.agro.ui.fragments.sales.fragment

import `in`.global.agro.R
import `in`.global.agro.callbacks.OptionsPopupCallback
import `in`.global.agro.callbacks.SalesOptionsListener
import `in`.global.agro.data.model.Products
import `in`.global.agro.data.model.SalesModel
import `in`.global.agro.databinding.FragmentSalesDetailsBinding
import `in`.global.agro.extensions.showSnackBar
import `in`.global.agro.ui.fragments.sales.adapter.SalesDetailAdapter
import `in`.global.agro.ui.popup.OptionsPopup
import `in`.global.agro.viewmodels.SalesViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SalesDetails : Fragment(R.layout.fragment_sales_details), SalesOptionsListener {
    private lateinit var binding: FragmentSalesDetailsBinding
    private lateinit var salesDetailAdapter: SalesDetailAdapter
    private val viewModel by activityViewModels<SalesViewModel>()
    private val args by navArgs<SalesDetailsArgs>()


    var xOff = 0F
    var yOff = 0F


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSalesDetailsBinding.bind(view)

        binding.customerName.text = args.salesDetail.name

        setUpRecyclerview()

        getSalesProductsForSpecificCustomer()


    }


    private fun getSalesProductsForSpecificCustomer() {
        lifecycleScope.launchWhenStarted {
            viewModel.getProductsForSpecificCustomer(args.salesDetail.id).collect {
                salesDetailAdapter.differ.submitList(it)

            }
        }

    }


    private fun setUpRecyclerview() {
        salesDetailAdapter = SalesDetailAdapter(this)
        binding.salesDetailRecyclerView.apply {
            adapter = salesDetailAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }


    @SuppressLint("ClickableViewAccessibility", "InflateParams")
    override fun onOptionsClick(salesModel: SalesModel, view: ViewGroup) {
        view.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    xOff = event.x
                    yOff = event.y
                }
            }
            false
        }

        val popup = OptionsPopup(
            layoutInflater.inflate(R.layout.options_popup, null),
            view,
            xOff,
            yOff
        )

        popup.setOnOptionsCallBack(object : OptionsPopupCallback {
            override fun onOptionsClick(value: String) {
                when (value) {

                    "delete" -> {
                        requireContext().showSnackBar(binding.root, "Deleted Successfully")
                        popup.dismiss()
                    }


                    "share", "save" -> {
                        val action = SalesDirections.actionGlobalPreview(salesModel)
                        findNavController().navigate(action)
                        popup.dismiss()

                    }


                }
            }
        })
    }


}