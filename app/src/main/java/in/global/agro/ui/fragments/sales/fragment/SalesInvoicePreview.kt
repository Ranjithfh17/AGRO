package `in`.global.agro.ui.fragments.sales.fragment

import `in`.global.agro.R
import `in`.global.agro.databinding.FragmentSalesInvoicePreviewBinding
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.itextpdf.text.*
import com.itextpdf.text.html.WebColors
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.*


class SalesInvoicePreview : Fragment(R.layout.fragment_sales_invoice_preview) {

    private lateinit var binding: FragmentSalesInvoicePreviewBinding
    private val args by navArgs<SalesInvoicePreviewArgs>()
    private lateinit var document: Document
    private lateinit var headerList: MutableList<String>
    private lateinit var childList: MutableList<String>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSalesInvoicePreviewBinding.bind(view)

        setDetails()

        binding.save.setOnClickListener {
            saveAsPdf()
        }


        headerList = mutableListOf(
            "Price",
            "Quantity",
            "Tax",
            "Total Amount"
        )


        childList = mutableListOf(
            args.salesInvoice.price,
            args.salesInvoice.quantity,
            args.salesInvoice.tax.toString(),
            args.salesInvoice.totalAmount,
        )


        binding.cancel.setOnClickListener {
            findNavController().popBackStack()
        }



    }

    private fun setDetails() {

        binding.apply {
            companyName.text = " SGS"
            phoneNo.text = " 8562524526"
            customerName.text = args.salesInvoice.name
            date.text = args.salesInvoice.date
            productName.text = "Product : " + args.salesInvoice.product
            price.text = args.salesInvoice.price
            quantity.text = args.salesInvoice.quantity
            tax.text = args.salesInvoice.tax
            totalAmount.text = args.salesInvoice.totalAmount
        }
    }




    private fun saveAsPdf() {

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

                val paragraphCompany = Paragraph("Company Name : SGS")
                paragraphCompany.alignment = Element.ALIGN_LEFT

                val paragraphPhoneNo = Paragraph("Phone No : 859455854555")
                paragraphPhoneNo.alignment = Element.ALIGN_LEFT
                paragraphPhoneNo.paddingTop = 50f

                val paragraphTitle = Paragraph("Sales Invoice")
                paragraphTitle.alignment = Element.ALIGN_CENTER

                val paragraphCustomerName = Paragraph("Bill To : ${args.salesInvoice.name}")
                paragraphCustomerName.alignment = Element.ALIGN_LEFT

                val paragraphDate = Paragraph("Date: ${args.salesInvoice.date}")
                paragraphDate.alignment = Element.ALIGN_RIGHT

                val paragraphProductName = Paragraph("Product Name : ${args.salesInvoice.product}")
                paragraphProductName.alignment = Element.ALIGN_LEFT

                cell.addElement(paragraphCompany)
                cell.addElement(paragraphPhoneNo)
                cell.addElement(paragraphTitle)
                cell.addElement(paragraphCustomerName)
                cell.addElement(paragraphDate)
                cell.addElement(paragraphProductName)
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


            } catch (exception: Exception) {
                Log.i("TAG", "saveAsPdf:${exception.message} ")
            } finally {
                document.close()
            }
        }
    }


}