package `in`.global.agro.ui.fragments.purchase

import `in`.global.agro.R
import `in`.global.agro.callbacks.OptionsPopupCallback
import `in`.global.agro.callbacks.PurchaseOptionsListener
import `in`.global.agro.data.model.PurchaseModel
import `in`.global.agro.data.model.SalesModel
import `in`.global.agro.databinding.FragmentPurchaseBinding
import `in`.global.agro.extensions.showSnackBar
import `in`.global.agro.ui.adapter.PurchaseAdapter
import `in`.global.agro.ui.popup.OptionsPopup
import `in`.global.agro.utils.KeyboardHelper
import `in`.global.agro.viewmodels.PurchaseViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.itextpdf.text.*
import com.itextpdf.text.html.WebColors
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.collections.ArrayList


class Purchase : Fragment(R.layout.fragment_purchase), PurchaseOptionsListener {

    private lateinit var binding: FragmentPurchaseBinding
    private lateinit var purchaseAdapter: PurchaseAdapter
    private val viewModel by activityViewModels<PurchaseViewModel>()
    private var xOff = 0F
    private var yOff = 0F
    private lateinit var document: Document
    private lateinit var headerList: MutableList<String>
    private var childList = mutableListOf<String>()
    private lateinit var purchaseList: List<PurchaseModel>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPurchaseBinding.bind(view)

        addPurchase()
        setUpRecyclerView()
        getAllPurchase()

        setHasOptionsMenu(true)

        headerList = mutableListOf(
            "Price",
            "Quantity",
            "Tax",
            "Total Amount"
        )

        searchPurchase()

    }


    private fun addPurchase() {
        binding.addPurchase.setOnClickListener {
            findNavController().navigate(R.id.addPurchase)
        }
    }

    private fun setUpRecyclerView() {
        purchaseAdapter = PurchaseAdapter(this)
        binding.purchaseRecyclerView.apply {
            adapter = purchaseAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun getAllPurchase() {
        lifecycleScope.launchWhenStarted {
            viewModel.getAllPurchase().collect {
                purchaseAdapter.differ.submitList(it)
                purchaseList = it

                for (i in it) {
                    childList.add(i.price)
                    childList.add(i.quantity)
                    childList.add(i.tax.toString())
                    childList.add(i.totalAmount)
                }


                if (it.isEmpty()) {
                    binding.emptyState.visibility = View.VISIBLE
                } else {
                    binding.emptyState.visibility = View.GONE
                }


            }
        }
    }

    @SuppressLint("ClickableViewAccessibility", "InflateParams")
    override fun onOptionsClick(purchaseModel: PurchaseModel, view: ViewGroup) {

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
                        val action = PurchaseDirections.actionGlobalPurchasePreview(purchaseModel)
                        findNavController().navigate(action)
                        popup.dismiss()

                    }

                }
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sales_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.search_menu) {
            if (binding.searchLayout.visibility == View.VISIBLE) {
                binding.searchLayout.visibility = View.GONE

            } else {

                binding.searchLayout.visibility = View.VISIBLE
                binding.searchView.requestFocus()
                KeyboardHelper.openKeyboard(binding.searchView, requireContext())

            }
        }

        if (item.itemId == R.id.pdf_menu) {
            saveAsPdf(childList, headerList)
        }

        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    private fun saveAsPdf(childList: MutableList<String>, headerList: MutableList<String>) {

        CoroutineScope(Dispatchers.Main).launch {

            try {
                document = Document()
                val path =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath + "/AGRO SOFT"
                val dir = File(path)

                if (!dir.exists()) {
                    dir.mkdirs()
                }

                val file = File(dir, "Purchase" + UUID.randomUUID() + ".pdf")
                val outputStream = FileOutputStream(file)
                PdfWriter.getInstance(document, outputStream)


                document.open()
                document.pageSize = PageSize.A4

                var cell = PdfPCell()
                cell.border = Rectangle.NO_BORDER
                cell.isUseAscender = true
                val header1 = PdfPTable(1)

                val paragraphCompany = Paragraph("AGRO")
                paragraphCompany.alignment = Element.ALIGN_LEFT


                cell.addElement(paragraphCompany)

                header1.addCell(cell)
                document.add(header1)


                val table = PdfPTable(4)
                val columnWidth = floatArrayOf(20f, 20f, 20f, 40f)
                table.setWidths(columnWidth)


                cell = PdfPCell()
                for (i in headerList.indices) {
                    cell = PdfPCell(Phrase(headerList[i]))
                    cell.backgroundColor = WebColors.getRGBColor("#5471f1")
                    table.addCell(cell)
                }


                for (i in childList.indices) {
                    cell = PdfPCell(Phrase(childList[i]))
                    table.addCell(cell)
                }


                document.add(table)

                requireContext().showSnackBar(binding.root, "PDF File saved successfully")


            } catch (exception: Exception) {
                Log.i("TAG", "saveAsPdf:${exception.message} ")
            } finally {
                document.close()
            }
        }
    }

    private fun searchPurchase() {
        binding.searchView.doOnTextChanged { text, _, _, _ ->
            filterSales(text.toString())
        }

    }

    private fun filterSales(name: String) {
        val searchList = mutableListOf<PurchaseModel>()
        for (purchase in purchaseList) {
            if (purchase.name.lowercase().contains(name) || purchase.product.lowercase()
                    .contains(name)
            ) {
                searchList.add(purchase)
            }
        }

        purchaseAdapter.differ.submitList(searchList)
    }



}
