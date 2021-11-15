package `in`.global.agro.ui.fragments.sales.fragment

import `in`.global.agro.R
import `in`.global.agro.callbacks.OptionsPopupCallback
import `in`.global.agro.callbacks.SalesDetailListener
import `in`.global.agro.callbacks.SalesOptionsListener
import `in`.global.agro.data.model.SalesModel
import `in`.global.agro.databinding.FragmentSalesBinding
import `in`.global.agro.extensions.showSnackBar
import `in`.global.agro.extensions.showToast
import `in`.global.agro.ui.adapter.SalesAdapter
import `in`.global.agro.ui.fragments.BaseFragment
import `in`.global.agro.ui.popup.OptionsPopup
import `in`.global.agro.utils.KeyboardHelper
import `in`.global.agro.viewmodels.SalesViewModel
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
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


class Sales : BaseFragment(),SalesDetailListener {

    private lateinit var binding: FragmentSalesBinding
    private lateinit var salesAdapter: SalesAdapter
    private val viewModel by activityViewModels<SalesViewModel>()

    private lateinit var document: Document
    private lateinit var pdfDocument: PdfDocument
    private lateinit var headerList: MutableList<String>
    private var childList = mutableListOf<String>()
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var hasStoragePermission = false
    private lateinit var salesList: List<SalesModel>


    private val docsLauncher = registerForActivityResult(ActivityResultContracts.CreateDocument()) {
        it?.let { uri ->
            requireContext().contentResolver.openOutputStream(uri).use { outputStream ->
                pdfDocument.writeTo(outputStream)


            }
        }

    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSalesBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        addSales()
        setUpRecyclerView()
        getSales()

        headerList = mutableListOf(
            "Customer Name",
            "product",
            "Price",
            "Quantity",
            "Tax",
            "Total Amount"
        )


        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    hasStoragePermission = it
                } else {
                    requireContext().showToast("Enable permission in settings to continue")
                }
            }

        searchSales()


    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sales_menu, menu)
    }


    private fun addSales() {
        binding.addSales.setOnClickListener {

            createCustomPdf()
            it.findNavController().navigate(R.id.addSales)
        }
    }


    private fun setUpRecyclerView() {
        salesAdapter = SalesAdapter(this)
        binding.apply {
            salesRecyclerView.adapter = salesAdapter
            salesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }


    private fun getSales() {
        lifecycleScope.launchWhenStarted {
            viewModel.getAllSales().collect {
                Log.i("TAG", "getSales: $it")
                salesAdapter.differ.submitList(it)
                salesList = it

                for (i in it) {
//                    childList.add(i.name)
//                    childList.add(i.product)
//                    childList.add(i.price)
//                    childList.add(i.quantity)
//                    childList.add(i.tax.toString())
//                    childList.add(i.totalAmount)
                }


                if (it.isEmpty()) {
                    binding.emptyState.visibility = View.VISIBLE
                } else {
                    binding.emptyState.visibility = View.GONE
                }

            }
        }
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

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                saveAsPdf(childList, headerList)
            } else {
                if (hasStoragePermission) {
                    saveAsPdf(childList, headerList)
                } else {
                    permissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }

        }



        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)

    }


    override fun onDetailClick(salesModel: SalesModel) {
        val action=SalesDirections.actionSalesToSalesDetails(salesModel)
        findNavController().navigate(action)
    }


    private fun saveAsPdf(childList: MutableList<String>, headerList: MutableList<String>) {

        CoroutineScope(Dispatchers.Main).launch {

            try {
                document = Document()

                val path =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath + "/AGRO BOOK"

                val dir = File(path)

                if (!dir.exists()) {
                    dir.mkdirs()
                }


                val file = File(dir, "Sales" + UUID.randomUUID() + ".pdf")
                val outputStream = FileOutputStream(file)
                PdfWriter.getInstance(document, outputStream)

                document.open()
                document.pageSize = PageSize.A4

                var cell = PdfPCell()
                cell.border = Rectangle.NO_BORDER
                cell.isUseAscender = true
                val header1 = PdfPTable(1)

                val paragraphCompany = Paragraph("AGRO BOOK")
                paragraphCompany.alignment = Element.ALIGN_LEFT
                paragraphCompany.paddingTop = 50f

                val paragraphTitle = Paragraph("Sales")
                paragraphTitle.alignment = Element.ALIGN_LEFT
                paragraphCompany.paddingTop = 30f

                cell.addElement(paragraphCompany)
                cell.addElement(paragraphTitle)

                header1.addCell(cell)
                document.add(header1)

                val table = PdfPTable(6)
                table.spacingBefore()

                val columnWidth = floatArrayOf(50f, 30f, 30f, 30f, 20f, 40f)
                table.setWidths(columnWidth)

                cell = PdfPCell()
                for (i in headerList.indices) {
                    cell = PdfPCell(Phrase(headerList[i]))
                    cell.setPadding(20f)
                    cell.backgroundColor = WebColors.getRGBColor("#5471f1")
                    table.addCell(cell)
                }


                for (i in childList.indices) {
                    cell = PdfPCell(Phrase(childList[i]))
                    table.addCell(cell)
                }

                document.add(table)

                requireContext().showSnackBar(binding.root, "PDF File saved successfully")

                headerList.clear()
                childList.clear()


            } catch (exception: Exception) {
                Log.i("TAG", "saveAsPdf:${exception.message} ")
            } finally {
                document.close()
            }
        }
    }


    private fun searchSales() {
        binding.searchView.doOnTextChanged { text, _, _, _ ->
            filterSales(text.toString())
        }

    }


    private fun filterSales(name: String) {
        val searchList = mutableListOf<SalesModel>()
        for (sales in salesList) {
            if (sales.name.lowercase().contains(name)) {
                searchList.add(sales)
            }
        }

        salesAdapter.differ.submitList(searchList)
    }


    private fun createCustomPdf() {

        val bitmapDescriptor = BitmapFactory.decodeResource(resources, R.drawable.icon_one)
        val scaledBitmap = Bitmap.createScaledBitmap(bitmapDescriptor, 150, 150, false)

        pdfDocument = PdfDocument()
        val paint = Paint()
        val titlePaint = Paint()

        val pageInfo = PdfDocument.PageInfo.Builder(1120, 2000, 1).create()
        val page1 = pdfDocument.startPage(pageInfo)

        val canvas = page1.canvas
//        canvas.drawBitmap(scaledBitmap, 30f, 40f, paint)

        titlePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        titlePaint.textSize = 24f
        titlePaint.color = ContextCompat.getColor(requireContext(), R.color.primaryTextColor)
        titlePaint.textAlign=Paint.Align.CENTER
        titlePaint.isAntiAlias=true



        canvas.drawText("Agro Book\n Sales Invoice", 100f, 50f, titlePaint)
        canvas.drawText("Company Name  :  ".plus("hello"), 100f, 80f, titlePaint)
        pdfDocument.finishPage(page1)


        try {
            val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath + "/AGRO BOOK"

            val dir = File(path)

            if (!dir.exists()) {
                dir.mkdirs()
            }

            val file = File(dir, "Sample" + UUID.randomUUID() + ".pdf")
            val outputStream = FileOutputStream(file)
            pdfDocument.writeTo(outputStream)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}